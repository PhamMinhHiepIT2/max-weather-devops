# CI/CD Documentation

## Overview

This document describes the Continuous Integration and Continuous Deployment (CI/CD) flow for both **Staging** and **Production** environments.  
The pipelines are implemented in Jenkins and automate the process of building, publishing, and deploying Docker images to Kubernetes.

---

## CI/CD Flow

### 1. Staging Pipeline

**Steps:**
1. Check out the latest code from the `main` branch.  
2. Build a Docker image using the **tag** provided from the Jenkins job input.  
3. Push the built image to **Amazon ECR**.  
4. Update the Kubernetes manifest file with the new image tag.  
5. Apply the updated manifest to the Kubernetes cluster.  
6. Commit and push the manifest changes back to the Git repository.

---

### 2. Production Pipeline

**Steps:**
1. Check out the latest code from the `main` branch.  
2. Verify that the Docker image with the provided **tag** exists in **Amazon ECR**.  
3. Update the Kubernetes manifest file with the verified image tag.  
4. Apply the updated manifest to the Kubernetes cluster.  
5. Commit and push the manifest changes back to the Git repository.

## Result

## Staging pipeline
![Staging pipeline](./images/image4.png)

When triggering the **Staging** pipeline, we must provide the Docker image tag as a parameter.  
The tag should follow **semantic versioning** (e.g., `1.0.0`, `1.1.2`) to maintain version consistency and traceability.

## Staging success pipeline
![Staging success pipeline](./images/image5.png)

The pipeline runs successfully after completing all stages — including building, pushing the image to ECR, updating manifests, and deploying to the Staging environment.

## Production pipeline
![Production pipeline](./images/image3.png)

When triggering the **Production** pipeline, provide the image tag that has already been **tested and verified** in the Staging environment.  
This ensures that only validated builds are deployed to production.

## Production fail pipeline
![Production fail pipeline](./images/image1.png)

If the specified image tag **does not exist** in the ECR repository, the pipeline will fail during the verification stage.  
This prevents untested or invalid images from being deployed to production.

## Production success pipeline
![Production success pipeline](./images/image2.png)

If the specified image tag **exists** in ECR, the pipeline proceeds successfully — updating the Kubernetes manifests and deploying the application to the k8s cluster.

## K8S pods
![K8S pods](./images/image6.png)

After the pipeline completes successfully, the new version of the service is deployed to the **Amazon EKS** cluster.  
We can verify the running pods and their statuses using `kubectl get pods`.
