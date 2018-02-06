insert into t_baby_user_info (addr_name, addr_pos_x, addr_pos_y, user_role, tel, note, user_code, user_name) values ('太平保健大厦', 22.496849627028876, 113.92369762250004, 'COMP', '13412312312', '机构游客', 'Usjigou1231543434', '机构游客');
insert into t_baby_user_info (addr_name, addr_pos_x, addr_pos_y, user_role, tel, note, user_code, user_name) values ('中山大学', 22.50341238872523, 113.90493580437654, 'BB', '13312312312', '用户游客', 'Usyouke1231543434', '用户游客');

INSERT INTO t_baby_info (baby_name, baby_birthday, baby_sex, create_time, baby_code)VALUES ('宝贝男', '2010-02-23 03:30:30','M', NOW(), 'Bbcode123123123');
INSERT INTO t_baby_info (baby_name, baby_birthday, baby_sex, create_time, baby_code)VALUES ('宝贝女', '2012-08-12 03:30:30','F', NOW(), 'Bbcode456456456');

INSERT INTO t_baby_user_baby (user_code, baby_code, create_time)VALUES ('Usyouke1231543434', 'Bbcode123123123',NOW());
INSERT INTO t_baby_user_baby (user_code, baby_code, create_time)VALUES ('Usyouke1231543434', 'Bbcode456456456',NOW());