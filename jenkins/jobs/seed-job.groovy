// Seed job to bootstrap all Jenkins pipelines from Git repository
// This job will pull DSL scripts from external Git repository and execute them

job('seed-jobs') {
    description('Seed job to create all Jenkins pipelines for Max Weather API from Git repository')
    label('master')
    
    triggers {
        // Run every 5 minutes to check for changes
        cron('H/5 * * * *')
        
        // Also run on startup
        startup('H/1 * * * *')
    }
    
    scm {
        git {
            remote {
                name('origin')
                url('https://github.com/PhamMinhHiepIT2/max-weather-devops.git')
                credentials('github-token') // Use GitHub credentials
            }
            branch('main')
        }
    }
    
    steps {
        // Execute all DSL scripts from the Git repository
        dsl {
            // Process all Groovy files in the jenkins/jobs directory from Git
            external('jenkins/jobs/*.groovy')
            
            // Additional DSL scripts can be added here
            text('''
                // Create a cleanup job
                job('cleanup-old-builds') {
                    description('Clean up old builds and artifacts')
                    label('master')
                    
                    triggers {
                        cron('0 2 * * *') // Run daily at 2 AM
                    }
                    
                    steps {
                        shell('''
                            # Clean up old builds (keep last 10)
                            find /var/jenkins_home/jobs/*/builds -maxdepth 1 -type d -name "[0-9]*" | sort -n | head -n -10 | xargs rm -rf
                            
                            # Clean up old artifacts
                            find /var/jenkins_home/jobs/*/builds -name "*.log" -mtime +7 -delete
                            
                            echo "Cleanup completed"
                        ''')
                    }
                }
            ''')
        }
    }
    
    publishers {
        // Notify on success/failure
        mailer('admin@max-weather.com', false, true)
        
        // Archive build artifacts
        archiveArtifacts('**/*.log')
    }
}