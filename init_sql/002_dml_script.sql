--
-- PostgreSQL database dump
--


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.users (username, password, enabled) FROM stdin;
jatinuser	$2a$10$fhX1.PWpi3dK1h2AyMDBiunI9l32WaLhyOYsO5pxBhN14WedhdQVu	t
exampleUser	$2a$10$RGYUKPPZOSfZQmA08wBdh.oLwvScBLUDaqaZgTBcG5Q8p5IdONoIW	t
abc	$2a$10$BMkHhYKAuoBaTyXrFVxNc.LUEztv3KDTC6N28UtD7pHztGFHUSrBq	t
xyz	$2a$10$8KIjT1FrwXepD7UE/mNpm.e/l6PYhLrO13vI4A8B3yEr49osKVwyG	t
qq	$2a$10$XUbNkUfwSeqVmIYYJv227epeNZUK0hGaikzdyDU/opnqot4sNE6k2	t
qqq	$2a$10$l9f1ktbJzPZDyC93MzSqbuwPz8rKHaj8fwG1O66EG7fvaUbTzhkoy	t
\.


--
-- Data for Name: authorities; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.authorities (username, authority) FROM stdin;
exampleUser	ROLE_USER
abc	ROLE_USER
xyz	ROLE_USER
qq	ROLE_USER
qqq	ROLE_USER
jatinuser	ADMIN
\.



--
-- PostgreSQL database dump complete
--

