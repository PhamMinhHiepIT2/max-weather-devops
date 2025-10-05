variable "region" {
  description = "AWS region"
  type        = string
}

variable "cluster_name" {
  description = "EKS cluster name"
  type        = string
}

variable "oidc_issuer_url" {
  description = "OIDC issuer URL from EKS (e.g., https://oidc.eks.<region>.amazonaws.com/id/xxxx)"
  type        = string
}

variable "manage_oidc_provider" {
  description = "Whether this module should create the IAM OIDC provider (set false to use existing)"
  type        = bool
  default     = false
}

variable "permission_boundary_arn" {
  description = "Optional IAM permissions boundary to attach to roles"
  type        = string
  default     = null
}

variable "tags" {
  description = "Common tags"
  type        = map(string)
  default     = {}
}

variable "enable_external_dns" {
  description = "Whether to create ExternalDNS IRSA role"
  type        = bool
  default     = false
}

variable "app_service_account" {
  description = "K8s service account name for app IRSA (namespace/name)"
  type        = string
  default     = "default/weather-api"
}

variable "app_s3_bucket_arns" {
  description = "S3 bucket ARNs the app may access"
  type        = list(string)
  default     = []
}

variable "app_dynamodb_table_arns" {
  description = "DynamoDB table ARNs the app may access"
  type        = list(string)
  default     = []
}

# Karpenter
variable "enable_karpenter" {
  description = "Whether to create IAM roles for Karpenter"
  type        = bool
  default     = false
}

variable "karpenter_service_account" {
  description = "Kubernetes service account for Karpenter controller (namespace/name)"
  type        = string
  default     = "karpenter/karpenter"
}

