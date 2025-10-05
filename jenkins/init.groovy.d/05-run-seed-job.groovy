import jenkins.model.Jenkins
import hudson.plugins.git.GitSCM
import hudson.plugins.git.UserRemoteConfig
import hudson.plugins.git.BranchSpec
import hudson.triggers.TimerTrigger
import hudson.tasks.Shell

def instance = Jenkins.getInstance()

try {
    // Wait for Jenkins to be fully initialized
    Thread.sleep(10000)
    
    // Create a seed job if it doesn't exist
    def seedJobName = "seed-jobs"
    def seedJob = instance.getItem(seedJobName)
    
    if (seedJob == null) {
        // Create a freestyle job with Git SCM
        def job = instance.createProject(hudson.model.FreeStyleProject.class, seedJobName)
        
        // Set job description
        job.setDescription("Seed job to create all Jenkins pipelines for Max Weather API from Git repository")
        
        // Configure Git SCM
        def gitScm = new GitSCM(
            [new UserRemoteConfig("https://github.com/PhamMinhHiepIT2/max-weather-devops.git", "origin", "", "github-token")],
            [new BranchSpec("main")],
            false,
            null,
            null,
            null,
            null
        )
        job.setScm(gitScm)
        
        // Add build step to execute DSL scripts
        def buildStep = new Shell("""
#!/bin/bash
echo "Seed job executed - Pulling DSL scripts from Git repository"
echo "Repository: https://github.com/PhamMinhHiepIT2/max-weather-devops.git"
echo "Branch: main"
echo "DSL Scripts: jenkins/jobs/*.groovy"
echo ""
echo "This job will:"
echo "1. Pull latest changes from Git repository"
echo "2. Execute all DSL scripts in jenkins/jobs/ directory"
echo "3. Create/update all Jenkins pipeline jobs"
echo ""
echo "To use this seed job:"
echo "1. Update the Git repository URL in job configuration"
echo "2. Configure GitHub credentials"
echo "3. Install Job DSL plugin"
echo "4. Run this job to create all pipelines"
""")
        job.getBuildersList().add(buildStep)
        
        // Add build triggers
        def trigger = new TimerTrigger("H/5 * * * *") // Run every 5 minutes
        job.addTrigger(trigger)
        
        job.save()
        
        // Trigger the seed job
        job.scheduleBuild()
        println "Seed job created with Git SCM - ready for DSL execution from repository"
    } else {
        println "Seed job already exists - triggering build"
        seedJob.scheduleBuild()
    }
} catch (Exception e) {
    println "Could not create seed job: ${e.message}"
    e.printStackTrace()
}
