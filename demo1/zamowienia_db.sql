PGDMP  3    1                |           zamowienia_db    15.7    16.3                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                       0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    24590    zamowienia_db    DATABASE     �   CREATE DATABASE zamowienia_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Polish_Poland.1250';
    DROP DATABASE zamowienia_db;
                postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                pg_database_owner    false                       0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   pg_database_owner    false    5            Y           1247    24639    zamowienie_typ    TYPE     a   CREATE TYPE public.zamowienie_typ AS ENUM (
    'pyszne',
    'piechota',
    'telefonicznie'
);
 !   DROP TYPE public.zamowienie_typ;
       public          postgres    false    5            �            1259    24614    pozycja_zamowienie    TABLE       CREATE TABLE public.pozycja_zamowienie (
    id integer NOT NULL,
    cena double precision,
    opis character varying(255),
    sposob_platnosci character varying(255),
    status character varying(255),
    zamawiajacy character varying(255),
    id_zamowienia uuid
);
 &   DROP TABLE public.pozycja_zamowienie;
       public         heap    postgres    false    5            �            1259    24635    pozycja_zamowienie_seq    SEQUENCE     �   CREATE SEQUENCE public.pozycja_zamowienie_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public.pozycja_zamowienie_seq;
       public          postgres    false    5            �            1259    24603 
   zamowienie    TABLE     �   CREATE TABLE public.zamowienie (
    link character varying(255),
    do_kiedy character varying(255),
    typ character varying(255),
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    data timestamp(6) without time zone
);
    DROP TABLE public.zamowienie;
       public         heap    postgres    false    5    5    5            �            1259    24636    zamowienie_seq    SEQUENCE     x   CREATE SEQUENCE public.zamowienie_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.zamowienie_seq;
       public          postgres    false    5                      0    24614    pozycja_zamowienie 
   TABLE DATA           r   COPY public.pozycja_zamowienie (id, cena, opis, sposob_platnosci, status, zamawiajacy, id_zamowienia) FROM stdin;
    public          postgres    false    216   t                 0    24603 
   zamowienie 
   TABLE DATA           C   COPY public.zamowienie (link, do_kiedy, typ, id, data) FROM stdin;
    public          postgres    false    215   V                  0    0    pozycja_zamowienie_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.pozycja_zamowienie_seq', 6951, true);
          public          postgres    false    217                       0    0    zamowienie_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.zamowienie_seq', 551, true);
          public          postgres    false    218            z           2606    32884    zamowienie unique_uuid 
   CONSTRAINT     O   ALTER TABLE ONLY public.zamowienie
    ADD CONSTRAINT unique_uuid UNIQUE (id);
 @   ALTER TABLE ONLY public.zamowienie DROP CONSTRAINT unique_uuid;
       public            postgres    false    215            |           2606    32882    zamowienie zamowienie_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.zamowienie
    ADD CONSTRAINT zamowienie_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.zamowienie DROP CONSTRAINT zamowienie_pkey;
       public            postgres    false    215            }           2606    32885 8   pozycja_zamowienie pozycja_zamowienie_id_zamowienia_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.pozycja_zamowienie
    ADD CONSTRAINT pozycja_zamowienie_id_zamowienia_fkey FOREIGN KEY (id_zamowienia) REFERENCES public.zamowienie(id);
 b   ALTER TABLE ONLY public.pozycja_zamowienie DROP CONSTRAINT pozycja_zamowienie_id_zamowienia_fkey;
       public          postgres    false    215    216    3196               �  x����n\7Eׯ���H.������8@�p���\���vk�~���Q,޺4[��l��=���ύ�HVG2�$1ܼ�2|1��v��l�����e�\X�c�<u������9�+w��dis~��y��~����6&2�vk��fRKèO-D��ءG�7�g���JP�@}�ۃ*{��� e7x����x<.��ؚ��Y#��ʆ�-��㥺jJ��߈ވ���"W�ߗ���)蟷�����D�#�2���H�K���Ϊ>t���b�d���)�0|=",&�+D�g�
�^�w��ӳ��yo���F�]����-C�:${�)��ޜ�������4��E�ð�jJuR �T(��K�_]c���07;A���X��,����EлRٵ�-�J��٢�ǵ̏ePg��^@|���wp����v�S��׏�Q��h��q���jo"F�y��F��>p�$��i�1�P�X:�a��DNٸ��ID�� ��%�|�ˠ��n�`�Q��=�c|v�� �K�K��VDV3�"Ul2~f�E��!P?��c�F������u�
2�\��'��,�L����u��gzU�U�,9Ǘ��Y�7���N� [�`m���.��P<k;�W�n:�{;�~\���{��s��Ӝ�x�u��ӏ���v����O���*�f)#��XK��s�>�?������Ñ���&�Bi�kz���U��h¿��b(C.�;A���ј��19:�Z}�¹bh�i_����>A��FS�1�j�]3$-~!�����Lz��ʰ�Ԟ��0`�3��\�lC��h��o3�����a���]J��G�l/M������*v�w��W�^ߧ��F�/��>Ɍ�U���Fh�:�D�Q����M����p��������,���Fo[�&ʚ<V��%0%�� ��%��r?�kw6LM��ې*̐Ϧ%�y�%eP:��=��Ar�Α���n8�b#�U���X�@u������*�6h0���1�I�A�Ⓩ�ô~���_��?��[����;�q�KJ}�^����V�
7D�Rp�UQ��Ń��b����!�YĤ�4��(�=�C.�~Ƴ޶L#�"n(�(J�~���X�*ŃN��n�?Ï��]����ަD6����\��d��[�5��?������l=ik�n+"��Ne�b��h�p?N�TW6�/���q��cC�d;�U�v`�����|��         0  x��WK�\9\g��/��")2w}����ah�v��'�vUe��b����S�`D����e�rYuy��x�����Cg6�ׅ"q�R�#�[J����^_^q�q��a�TS�`yƐ��\J�I��<Fz�)I��b<Y��9k��}��'��+9F��������>�P�2��`]Ӟ�C˃��Ŧ�F�|F���Y4�^._����Ӽ0ъ[�}怯gh��+[+�nZ����n,�GJf�UKJ���*�.�f�It�(�[��h����/���?��֋�H�<�q���>p`%�@	�=���eMV�/8"���Qe�-d�)��,�L��ԟ�� �(_k����8�a������H	$=���<'�����F���	NkԒ{	����l�Z�/Z�If�X��?��Hn��B��߿�s�||��B��Cj	.�o��x�0�RL�6!z��;��]��^ ���f̷�Bӄ������#��V�_�b��M���)
��q���z¤*:T��2�~nU�מ�n=�8M�!bi("���i���|�cÂ5���6�ύHZp���@1��Ȃ��^�6�7rN_U�Dw���aI��`���j�F��p�_ޤR���Yr����0H.[����j*ො�lɑ
�B{%�ON�X�r�yq���Mm_TJ�'�oR�⒯/OhiVxƢ���^V�<L6�:W�N}Ƨ���HG,Is:�ѪK'�l�U�ms�	
���)I��V�bY�?�]~���0�Z��!J��c�!	��k�/���ljQ�_��
7RR68�����gM5-I�y�|���c�����-q⬾ a]�jqmC���p9S��X��ܴj��`�F�!'Ki�2l�Mo�$�Q9��Z��l8,��*�Cf"u2����EW\փrŊu���RGҶԞ���H�?�wyȇA���̼pԵi�baT�X˚!�3\�ǖd����Vu!�p�)d�4@���e`��%]��;ǪϪvf���qJ'2��+<��U��?�5��.���H�%u�1�wD�P_��FN1���G���Y���Np�fA�n�3	�s��%jޥ�3�XȨؙW�E�"�#9v�;ċ�6Wz��k:��]E�aL��:E��԰��E,=��g%��L���,��<��5Z4�H�ϰ(C�����b�w"(n+�(h]'��Q��<��02��uKX,h�*��)�|F��I�鄖��0�"ŋ�M��=N��3��`L��,;(���a���8X0�/��b<�R�xFC�؁�M�����[���u�&�9	�YQ&�^��v�P,&�����Uƒam�=|P�aVX�J'8Nw��M��o�ރ0�q����>j�����K�uy�V�pro�$Qv��$�SF�M�
�?�~�w[}Z�B�Ăđ���m��̺j��e��D�rLaX�6��C��hۏ��*�������W����6��<��҄6�aAx�]���;�JN����-��6JW������A@c��\�	6ʷ��.�ϰ��T�lh�a�K��wBEP=�O��*[�����
J�@u�7�#zc۱��]}�yM��9���$zw-%��!~�1vԋ�#|�ck�'\�Mn�#�	�J	�h��/p-<�,T�uW)T��)ΌX���C�ց޿W.o{�`ظ:b�|#��N�f|�\p:n�R�\�]�u����B1����������6rq�i�n�Uѣ�Zm�����y�@����h�S��,
z�PP�vdἙ�^��yd�����s�J��n�sm��z.	�Xч+�ۘ��;��ɶ��w8t�^i펇��"x{����K���#ܶ0��S��6�������2ܪU���~��lO`�ҷnSN���S앐w���%K��86�6¦�[��&h���gT��?R���
�|D{�	��Dé��'�-=�#�<,.?���:)J�)��~�XdL����ٷbmI�����oO4Y�.x*��p��.觲:x�M�'H�|��z��^��r�wR     