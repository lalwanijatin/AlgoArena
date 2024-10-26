--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2 (Debian 16.2-1.pgdg120+2)
-- Dumped by pg_dump version 16.2 (Debian 16.2-1.pgdg120+2)



CREATE TABLE public.authorities (
    username character varying(50) NOT NULL,
    authority character varying(50) NOT NULL
);


ALTER TABLE public.authorities OWNER TO root;

------

--
-- Name: problem; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.problem (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    name character varying(255) NOT NULL,
    description text,
    hidden boolean DEFAULT true,
    solved_count integer DEFAULT 0,
    created_at timestamp with time zone DEFAULT now(),
    updated_at timestamp with time zone DEFAULT now(),
    difficulty character varying(10) DEFAULT 'EASY'::character varying,
    acceptance integer,
    CONSTRAINT difficulty_check CHECK (((difficulty)::text = ANY ((ARRAY['EASY'::character varying, 'MEDIUM'::character varying, 'HARD'::character varying])::text[])))
);


ALTER TABLE public.problem OWNER TO root;


--
-- Name: submission; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.submission (
    id uuid DEFAULT gen_random_uuid() NOT NULL,
    problem_id uuid NOT NULL,
    username character varying(255) NOT NULL,
    code text NOT NULL,
    active_contest_id uuid,
    created_at timestamp with time zone DEFAULT now(),
    updated_at timestamp with time zone,
    status character varying(20) DEFAULT 'PENDING'::character varying,
    memory integer,
    "time" double precision
);


ALTER TABLE public.submission OWNER TO root;

ALTER TABLE public.submissions ADD submissionid uuid;


--
-- Name: users; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.users (
    username character varying(50) NOT NULL,
    password character varying(68) NOT NULL,
    enabled boolean NOT NULL
);


ALTER TABLE public.users OWNER TO root;



ALTER TABLE ONLY public.problem
    ADD CONSTRAINT problem_pkey PRIMARY KEY (id);


--
-- Name: submission submission_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.submission
    ADD CONSTRAINT submission_pkey PRIMARY KEY (id);



--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (username);



--
-- Name: authorities authorities_username_fkey; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.authorities
    ADD CONSTRAINT authorities_username_fkey FOREIGN KEY (username) REFERENCES public.users(username);


--
-- Name: submission fk_problem; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.submission
    ADD CONSTRAINT fk_problem FOREIGN KEY (problem_id) REFERENCES public.problem(id);


--
-- Name: submissions fk_submissionid; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.submissions
    ADD CONSTRAINT fk_submissionid FOREIGN KEY (submissionid) REFERENCES public.submission(id);


--
-- Name: submission fk_username; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.submission
    ADD CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES public.users(username);


--
-- PostgreSQL database dump complete
--

