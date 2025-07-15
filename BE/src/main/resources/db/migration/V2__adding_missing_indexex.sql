
-- Indexes for booking queries optimization

CREATE INDEX IF NOT EXISTS idx_booking_service_provider_id ON booking_details(service_provider_id);
CREATE INDEX IF NOT EXISTS idx_booking_customer_id ON booking_details(customer_id);
CREATE INDEX IF NOT EXISTS idx_user_id ON users(id);