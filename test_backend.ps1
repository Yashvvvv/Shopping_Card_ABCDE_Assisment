# Test script for backend API endpoints
Write-Host "Testing Backend API Endpoints..." -ForegroundColor Green

# Test getting items (no auth required)
Write-Host "`nTesting GET /items..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/items" -Method GET
    Write-Host "✓ GET /items successful. Found $($response.Count) items" -ForegroundColor Green
} catch {
    Write-Host "✗ GET /items failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test adding item to cart (without auth - should use testuser)
Write-Host "`nTesting POST /carts (add item to cart without auth)..." -ForegroundColor Yellow
try {
    $cartRequest = @{
        itemIds = @(1, 2)
    } | ConvertTo-Json
    
    $response = Invoke-RestMethod -Uri "http://localhost:8080/carts" -Method POST -Body $cartRequest -ContentType "application/json"
    Write-Host "✓ POST /carts successful. Cart ID: $($response.id)" -ForegroundColor Green
} catch {
    Write-Host "✗ POST /carts failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test getting cart
Write-Host "`nTesting GET /carts/my-cart..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8080/carts/my-cart" -Method GET
    Write-Host "✓ GET /carts/my-cart successful. Cart has $($response.cartItems.Count) items" -ForegroundColor Green
} catch {
    Write-Host "✗ GET /carts/my-cart failed: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`nBackend API test completed!" -ForegroundColor Green
