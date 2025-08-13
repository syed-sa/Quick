-- V1__init_schema.sql 

-- Table: booking_details
CREATE TABLE public.booking_details (
    id bigint NOT NULL,
    booking_status character varying(255),
    created_at timestamp(6) without time zone,
    customer_id bigint,
    description character varying(255),
    is_active boolean NOT NULL,
    location character varying(255),
    service_id bigint,
    service_name character varying(255),
    service_provider_id bigint
);

-- Sequence: booking_details_id_seq
CREATE SEQUENCE public.booking_details_id_seq START WITH 1 INCREMENT BY 1 CACHE 1;
ALTER SEQUENCE public.booking_details_id_seq OWNED BY public.booking_details.id;
ALTER TABLE ONLY public.booking_details ALTER COLUMN id SET DEFAULT nextval('public.booking_details_id_seq'::regclass);

-- Table: buisness_category
CREATE TABLE public.buisness_category (
    id bigint NOT NULL,
    name character varying(255)
);

-- Sequence: buisness_category_id_seq
CREATE SEQUENCE public.buisness_category_id_seq START WITH 1 INCREMENT BY 1 CACHE 1;
ALTER SEQUENCE public.buisness_category_id_seq OWNED BY public.buisness_category.id;
ALTER TABLE ONLY public.buisness_category ALTER COLUMN id SET DEFAULT nextval('public.buisness_category_id_seq'::regclass);

-- Table: notification
CREATE TABLE public.notification (
    id bigint NOT NULL,
    booking_id bigint NOT NULL,
    message character varying(255),
    notification_title character varying(255),
    notification_type character varying(255),
    priority character varying(255),
    read boolean,
    "timestamp" timestamp(6) without time zone,
    user_id bigint
);

-- Sequence: notification_id_seq
CREATE SEQUENCE public.notification_id_seq START WITH 1 INCREMENT BY 1 CACHE 1;
ALTER SEQUENCE public.notification_id_seq OWNED BY public.notification.id;
ALTER TABLE ONLY public.notification ALTER COLUMN id SET DEFAULT nextval('public.notification_id_seq'::regclass);

-- Table: refresh_token
CREATE TABLE public.refresh_token (
    id bigint NOT NULL,
    expiry_date timestamp(6) without time zone,
    is_revoked boolean NOT NULL,
    token character varying(255),
    user_id bigint
);

-- Sequence: refresh_token_id_seq
CREATE SEQUENCE public.refresh_token_id_seq START WITH 1 INCREMENT BY 1 CACHE 1;
ALTER SEQUENCE public.refresh_token_id_seq OWNED BY public.refresh_token.id;
ALTER TABLE ONLY public.refresh_token ALTER COLUMN id SET DEFAULT nextval('public.refresh_token_id_seq'::regclass);

-- Table: services
CREATE TABLE public.services (
    id bigint NOT NULL,
    address character varying(255),
    business_category_id bigint NOT NULL,
    city character varying(255),
    company_name character varying(255),
    email character varying(255),
    phone character varying(255),
    postal_code character varying(255),
    user_id bigint NOT NULL,
    website character varying(255)
);

-- Sequence: services_id_seq
CREATE SEQUENCE public.services_id_seq START WITH 1 INCREMENT BY 1 CACHE 1;
ALTER SEQUENCE public.services_id_seq OWNED BY public.services.id;
ALTER TABLE ONLY public.services ALTER COLUMN id SET DEFAULT nextval('public.services_id_seq'::regclass);

-- Table: users
CREATE TABLE public.users (
    id bigint NOT NULL,
    email character varying(255),
    name character varying(255),
    password character varying(255),
    phone character varying(255)
);

-- Sequence: users_id_seq
CREATE SEQUENCE public.users_id_seq START WITH 1 INCREMENT BY 1 CACHE 1;
ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;
ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);

-- Constraints
ALTER TABLE ONLY public.booking_details ADD CONSTRAINT booking_details_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.buisness_category ADD CONSTRAINT buisness_category_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.notification ADD CONSTRAINT notification_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.refresh_token ADD CONSTRAINT refresh_token_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.services ADD CONSTRAINT services_pkey PRIMARY KEY (id);
ALTER TABLE ONLY public.users ADD CONSTRAINT users_pkey PRIMARY KEY (id);

-- Unique constraints
ALTER TABLE ONLY public.users ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);
ALTER TABLE ONLY public.users ADD CONSTRAINT uk_du5v5sr43g5bfnji4vb8hg5s3 UNIQUE (phone);
ALTER TABLE ONLY public.services ADD CONSTRAINT ukk0ysgtww7qt38wq4l33d9aglv UNIQUE (user_id, company_name);

-- Indexes for booking queries optimization

CREATE INDEX IF NOT EXISTS idx_booking_service_provider_id ON booking_details(service_provider_id);
CREATE INDEX IF NOT EXISTS idx_booking_customer_id ON booking_details(customer_id);
CREATE INDEX IF NOT EXISTS idx_user_id ON users(id);
