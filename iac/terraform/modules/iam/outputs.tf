# EKS cluster and node role outputs are managed by the EKS module

output "oidc_provider_arn" {
  value       = coalesce(try(aws_iam_openid_connect_provider.this[0].arn, null), try(data.aws_iam_openid_connect_provider.this[0].arn, null))
  description = "OIDC provider ARN"
}

output "external_dns_role_arn" {
  value       = try(aws_iam_role.external_dns[0].arn, null)
  description = "IRSA role ARN for ExternalDNS (if enabled)"
}


output "lambda_authorizer_role_arn" {
  value       = aws_iam_role.lambda_authorizer.arn
  description = "Lambda authorizer execution role ARN"
}

output "aws_load_balancer_controller_role_arn" {
  value       = aws_iam_role.aws_load_balancer_controller.arn
  description = "AWS Load Balancer Controller role ARN"
}

output "karpenter_controller_role_arn" {
  value       = try(aws_iam_role.karpenter_controller[0].arn, null)
  description = "Karpenter controller IRSA role ARN (if enabled)"
}

output "karpenter_node_role_arn" {
  value       = try(aws_iam_role.karpenter_node[0].arn, null)
  description = "Karpenter node role ARN (if enabled)"
}

output "karpenter_instance_profile_name" {
  value       = try(aws_iam_instance_profile.karpenter[0].name, null)
  description = "Karpenter instance profile name (if enabled)"
}

