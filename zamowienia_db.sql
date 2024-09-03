PGDMP  6        	            |           zamowienia_db    15.7    16.3     	           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            
           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    24590    zamowienia_db    DATABASE     �   CREATE DATABASE zamowienia_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Polish_Poland.1250';
    DROP DATABASE zamowienia_db;
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                pg_database_owner    false                       0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   pg_database_owner    false    4            O           1247    24639    zamowienie_typ    TYPE     a   CREATE TYPE public.zamowienie_typ AS ENUM (
    'pyszne',
    'piechota',
    'telefonicznie'
);
 !   DROP TYPE public.zamowienie_typ;
       public          postgres    false    4            �            1259    24614    pozycja_zamowienie    TABLE       CREATE TABLE public.pozycja_zamowienie (
    id integer NOT NULL,
    cena double precision,
    opis character varying(255),
    sposob_platnosci character varying(255),
    status character varying(255),
    zamawiajacy character varying(255),
    id_zamowienie integer
);
 &   DROP TABLE public.pozycja_zamowienie;
       public         heap    postgres    false    4            �            1259    24635    pozycja_zamowienie_seq    SEQUENCE     �   CREATE SEQUENCE public.pozycja_zamowienie_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.pozycja_zamowienie_seq;
       public          postgres    false    4            �            1259    24603 
   zamowienie    TABLE     �   CREATE TABLE public.zamowienie (
    id bigint NOT NULL,
    link character varying(255),
    do_kiedy character varying(255),
    typ character varying(255)
);
    DROP TABLE public.zamowienie;
       public         heap    postgres    false    4            �            1259    24602    zamowienie_id_seq    SEQUENCE     �   CREATE SEQUENCE public.zamowienie_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public.zamowienie_id_seq;
       public          postgres    false    4    215                       0    0    zamowienie_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE public.zamowienie_id_seq OWNED BY public.zamowienie.id;
          public          postgres    false    214            �            1259    24636    zamowienie_seq    SEQUENCE     x   CREATE SEQUENCE public.zamowienie_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.zamowienie_seq;
       public          postgres    false    4            n           2604    24621    zamowienie id    DEFAULT     n   ALTER TABLE ONLY public.zamowienie ALTER COLUMN id SET DEFAULT nextval('public.zamowienie_id_seq'::regclass);
 <   ALTER TABLE public.zamowienie ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    214    215    215                      0    24614    pozycja_zamowienie 
   TABLE DATA           r   COPY public.pozycja_zamowienie (id, cena, opis, sposob_platnosci, status, zamawiajacy, id_zamowienie) FROM stdin;
    public          postgres    false    216   �                 0    24603 
   zamowienie 
   TABLE DATA           =   COPY public.zamowienie (id, link, do_kiedy, typ) FROM stdin;
    public          postgres    false    215   �                  0    0    pozycja_zamowienie_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.pozycja_zamowienie_seq', 1, false);
          public          postgres    false    217                       0    0    zamowienie_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.zamowienie_id_seq', 1, false);
          public          postgres    false    214                       0    0    zamowienie_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.zamowienie_seq', 401, true);
          public          postgres    false    218            r           2606    24620 *   pozycja_zamowienie pozycja_zamowienie_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public.pozycja_zamowienie
    ADD CONSTRAINT pozycja_zamowienie_pkey PRIMARY KEY (id);
 T   ALTER TABLE ONLY public.pozycja_zamowienie DROP CONSTRAINT pozycja_zamowienie_pkey;
       public            postgres    false    216            p           2606    24623    zamowienie zamowienie_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.zamowienie
    ADD CONSTRAINT zamowienie_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.zamowienie DROP CONSTRAINT zamowienie_pkey;
       public            postgres    false    215            s           2606    24656 3   pozycja_zamowienie fk_pozycja_zamowienie_zamowienie    FK CONSTRAINT     �   ALTER TABLE ONLY public.pozycja_zamowienie
    ADD CONSTRAINT fk_pozycja_zamowienie_zamowienie FOREIGN KEY (id_zamowienie) REFERENCES public.zamowienie(id);
 ]   ALTER TABLE ONLY public.pozycja_zamowienie DROP CONSTRAINT fk_pozycja_zamowienie_zamowienie;
       public          postgres    false    216    215    3184                  x�3��CAF\&�B1z\\\ Ȅ�         �   x�]�mn� ����S�=���RS�ڰI��/(2S��󾓁a�x;>�5�A��b��r����.Z"��.h��BQ��0TX*:*h9�����$�`�ҨZ_��h{�4CH�-����Ô}��C�xX�L������!�2��v�����	�a���R����:����k`"��y/ʯF%?�ȍ���~-K�Yo�m_b/7;�\���B�J�ƞ�*�J����e�ctF?��z��=n��?���t     