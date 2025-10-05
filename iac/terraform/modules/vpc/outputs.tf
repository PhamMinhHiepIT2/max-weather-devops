output "vpc_id" {
  description = "The ID of the VPC"
  value       = aws_vpc.this.id
}

output "public_subnet_ids" {
  description = "IDs of public subnets"
  value       = [for s in aws_subnet.public : s.id]
}

output "private_subnet_ids" {
  description = "IDs of private subnets"
  value       = [for s in aws_subnet.private : s.id]
}

output "private_route_table_ids" {
  description = "Route table IDs for private subnets"
  value       = [aws_route_table.private.id]
}

output "public_route_table_id" {
  description = "Route table ID for public subnets"
  value       = aws_route_table.public.id
}

output "nat_gateway_ids" {
  description = "NAT Gateway IDs by AZ index"
  value       = [aws_nat_gateway.this.id]
}

output "vpc_endpoint_ids" {
  description = "VPC Endpoint IDs (s3, ecr_api, ecr_dkr, logs)"
  value = {
    s3      = aws_vpc_endpoint.s3.id
    ecr_api = aws_vpc_endpoint.ecr_api.id
    ecr_dkr = aws_vpc_endpoint.ecr_dkr.id
    logs    = aws_vpc_endpoint.logs.id
  }
}

