insert into user_table (user_password, user_role, user_username, user_id)
values ('$2a$10$uXzoc5GccigVSmFpGV.FWuWGahO5KzBOn1g6VcTRUSf8vkG9dy/Fy', 'ADMIN', 'admin', user_sequence.nextval);

insert into user_table (user_password, user_role, user_username, user_id)
values ('$2a$10$ysQNTczy5Hmka4p4Gkb3L.rnklrymBA220SqlrNjBx.9gFEcXiHPa', 'USER', 'user', user_sequence.nextval);

insert into news_table (news_brief, news_content, news_date, news_title, news_id)
values ('velit vitae voluptatum! Asperiores, deleniti enim eos hic impedit natus officia rem saepe ' ||
        'temporibus voluptate! Autem consequuntur cum doloremque eos odit? Est eum explicabo fuga ipsa labore nesciunt ' ||
        'omnis praesentium recusandae suscipit totam? Ab atque consequuntur cupiditate debitis distinctio eos ' ||
        'exercitationem harum id, illum.',
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium alias asperiores at consequatur, cum ' ||
        'cumque dicta dolorum harum hic incidunt ipsam maxime molestias natus nemo nobis quae quis saepe similique ' ||
        'tempora ullam unde velit vitae voluptatum! Asperiores, deleniti enim eos hic impedit natus officia rem saepe ' ||
        'temporibus voluptate! Autem consequuntur cum doloremque eos odit? Est eum explicabo fuga ipsa labore nesciunt ' ||
        'omnis praesentium recusandae suscipit totam? Ab atque consequuntur cupiditate debitis distinctio eos ' ||
        'exercitationem harum id, illum ipsum laudantium libero magnam magni maxime natus nesciunt nisi numquam porro ' ||
        'quae quaerat quod reiciendis repellat repudiandae suscipit temporibus velit voluptates! Autem beatae debitis ' ||
        'dignissimos dolore doloremque doloribus eligendi est exercitationem hic iusto minima nemo nihil nisi nulla ' ||
        'numquam optio pariatur perferendis placeat quae quaerat quo quos reiciendis rem reprehenderit sunt totam, ullam ' ||
        'voluptatibus. A beatae distinctio ducimus error laborum molestiae quo voluptatum? A aliquid animi architecto ' ||
        'assumenda autem consectetur consequuntur doloribus, esse eveniet explicabo itaque labore magnam magni molestiae ' ||
        'nisi nobis nulla optio pariatur possimus quaerat quidem quisquam ratione reiciendis repellendus reprehenderit sed ' ||
        'tenetur ullam vitae, voluptate voluptates. Accusamus eaque illo itaque laudantium odio praesentium quibusdam quis. ' ||
        'Aperiam architecto aspernatur delectus dignissimos dolor eius est, id illum in iusto nemo officiis quae reiciendis ' ||
        'repellendus repudiandae rerum totam unde.',
        TIMESTAMP '2020-11-08 20:00:00.000000', 'Ipsum dolor sit', news_sequence.nextval);

insert into news_table (news_brief, news_content, news_date, news_title, news_id)
values ('Eveniet explicabo itaque labore magnam magni molestiae ' ||
        'nisi nobis nulla optio pariatur possimus quaerat quidem quisquam ratione reiciendis repellendus reprehenderit sed ' ||
        'tenetur ullam vitae, voluptate voluptates. Accusamus eaque illo itaque laudantium odio praesentium quibusdam quis. ' ||
        'Aperiam architecto aspernatur delectus dignissimos dolor eius est.',
        'Cumque dicta dolorum harum hic incidunt ipsam maxime molestias natus nemo nobis quae quis saepe similique ' ||
        'tempora ullam unde velit vitae voluptatum! Asperiores, deleniti enim eos hic impedit natus officia rem saepe ' ||
        'temporibus voluptate! Autem consequuntur cum doloremque eos odit? Est eum explicabo fuga ipsa labore nesciunt ' ||
        'omnis praesentium recusandae suscipit totam? Ab atque consequuntur cupiditate debitis distinctio eos ' ||
        'exercitationem harum id, illum ipsum laudantium libero magnam magni maxime natus nesciunt nisi numquam porro ' ||
        'quae quaerat quod reiciendis repellat repudiandae suscipit temporibus velit voluptates! Autem beatae debitis ' ||
        'dignissimos dolore doloremque doloribus eligendi est exercitationem hic iusto minima nemo nihil nisi nulla ' ||
        'numquam optio pariatur perferendis placeat quae quaerat quo quos reiciendis rem reprehenderit sunt totam, ullam ' ||
        'voluptatibus. A beatae distinctio ducimus error laborum molestiae quo voluptatum? A aliquid animi architecto ' ||
        'assumenda autem consectetur consequuntur doloribus, esse eveniet explicabo itaque labore magnam magni molestiae ' ||
        'nisi nobis nulla optio pariatur possimus quaerat quidem quisquam ratione reiciendis repellendus reprehenderit sed ' ||
        'tenetur ullam vitae, voluptate voluptates. Accusamus eaque illo itaque laudantium odio praesentium quibusdam quis. ' ||
        'Aperiam architecto aspernatur delectus dignissimos dolor eius est, id illum in iusto nemo officiis quae reiciendis ' ||
        'lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium alias asperiores at consequatur.',
        TIMESTAMP '2020-11-08 20:01:00.000000', 'Adipisicing elit', news_sequence.nextval);

insert into news_table (news_brief, news_content, news_date, news_title, news_id)
values ('Omnis praesentium recusandae suscipit totam? Ab atque consequuntur cupiditate debitis distinctio eos ' ||
        'exercitationem harum id, illum ipsum laudantium libero magnam magni maxime natus nesciunt nisi numquam porro ' ||
        'quae quaerat quod reiciendis repellat repudiandae.',
        'Tempora ullam unde velit vitae voluptatum! Asperiores, deleniti enim eos hic impedit natus officia rem saepe ' ||
        'temporibus voluptate! Autem consequuntur cum doloremque eos odit? Est eum explicabo fuga ipsa labore nesciunt ' ||
        'omnis praesentium recusandae suscipit totam? Ab atque consequuntur cupiditate debitis distinctio eos ' ||
        'exercitationem harum id, illum ipsum laudantium libero magnam magni maxime natus nesciunt nisi numquam porro ' ||
        'quae quaerat quod reiciendis repellat repudiandae suscipit temporibus velit voluptates! Autem beatae debitis ' ||
        'dignissimos dolore doloremque doloribus eligendi est exercitationem hic iusto minima nemo nihil nisi nulla ' ||
        'numquam optio pariatur perferendis placeat quae quaerat quo quos reiciendis rem reprehenderit sunt totam, ullam ' ||
        'voluptatibus. A beatae distinctio ducimus error laborum molestiae quo voluptatum? A aliquid animi architecto ' ||
        'assumenda autem  doloribus, esse eveniet explicabo itaque labore magnam magni molestiae ' ||
        'nisi nobis nulla optio pariatur possimus quaerat quidem quisquam ratione reiciendis repellendus reprehenderit sed ' ||
        'tenetur ullam vitae, voluptate voluptates. Accusamus eaque illo itaque laudantium odio praesentium quibusdam quis. ' ||
        'Aperiam architecto aspernatur delectus dignissimos dolor eius est, id illum in iusto nemo officiis quae reiciendis ' ||
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium alias asperiores at consequatur, cum ' ||
        'cumque dicta dolorum harum hic incidunt ipsam maxime molestias natus nemo nobis quae quis saepe similique ',
        TIMESTAMP '2020-11-08 20:02:00.000000', 'Assumenda beatae corporis', news_sequence.nextval);

insert into news_table (news_brief, news_content, news_date, news_title, news_id)
values ('Suscipit totam? Ab atque consequuntur cupiditate debitis distinctio eos ' ||
        'exercitationem harum id, illum ipsum laudantium libero magnam magni maxime natus nesciunt nisi numquam porro ' ||
        'quae quaerat quod reiciendis repellat repudiandae suscipit temporibus velit voluptates! Autem beatae debitis ' ||
        'dignissimos dolore doloremque doloribus',
        'Temporibus voluptate! Autem consequuntur cum doloremque eos odit? Est eum explicabo fuga ipsa labore nesciunt ' ||
        'omnis praesentium recusandae suscipit totam? Ab atque consequuntur cupiditate debitis distinctio eos ' ||
        'exercitationem harum id, illum ipsum laudantium libero magnam magni maxime natus nesciunt nisi numquam porro ' ||
        'quae quaerat quod reiciendis repellat repudiandae suscipit temporibus velit voluptates! Autem beatae debitis ' ||
        'dignissimos dolore doloremque doloribus eligendi est exercitationem hic iusto minima nemo nihil nisi nulla ' ||
        'numquam optio pariatur perferendis placeat quae quaerat quo quos reiciendis rem reprehenderit sunt totam, ullam ' ||
        'voluptatibus. A beatae distinctio ducimus error laborum molestiae quo voluptatum? A aliquid animi architecto ' ||
        'assumenda autem consectetur consequuntur doloribus, esse eveniet explicabo itaque labore magnam magni molestiae ' ||
        'nisi nobis nulla optio pariatur possimus quaerat quidem quisquam ratione reiciendis repellendus reprehenderit sed ' ||
        'tenetur ullam vitae, voluptate voluptates. Accusamus eaque illo itaque laudantium odio praesentium quibusdam quis. ' ||
        'Aperiam architecto aspernatur delectus dignissimos dolor eius est, id illum in iusto nemo officiis quae reiciendis ' ||
        'Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium alias asperiores at consequatur, cum ' ||
        'cumque dicta dolorum harum hic incidunt ipsam maxime molestias natus nemo nobis quae quis saepe similique ' ||
        'tempora ullam unde velit vitae voluptatum! Asperiores, deleniti enim eos hic impedit natus officia rem saepe ',
        TIMESTAMP '2020-11-08 20:03:00.000000', 'Illum in incidunt ipsum', news_sequence.nextval);

insert into news_table (news_brief, news_content, news_date, news_title, news_id)
values ('inventore quam sed totam! A ab aut blanditiis consequuntur debitis dolor dolore, ' ||
        'et facere fugiat fugit in ipsa itaque libero magnam maiores molestias nihil non, perspiciatis quasi quo quos ' ||
        'recusandae rerum sit unde vero.',
        'Velit voluptates voluptatum? Accusantium culpa exercitationem mollitia, odio qui soluta. A aperiam aspernatur, ' ||
        'eius explicabo facere fugiat hic, illum obcaecati quia quibusdam, quidem quod rem rerum! Ad aliquid architecto ' ||
        'eius eligendi, enim explicabo ipsa, iste laboriosam odio possimus quas quos soluta unde. Aliquam, consectetur ' ||
        'consequatur cumque deserunt dolorem exercitationem expedita facere iste itaque labore numquam quibusdam quis, ' ||
        'quisquam sunt voluptatum! Accusamus aspernatur beatae commodi dolorem id, incidunt, laboriosam molestiae numquam ' ||
        'repellendus soluta tempore voluptates. Ab dignissimos, dolorum error est et excepturi explicabo inventore magnam ' ||
        'maxime non officia porro reiciendis. Animi culpa cupiditate eos maxime minima molestias, neque non ratione ' ||
        'repudiandae rerum! Aliquid assumenda corporis, cupiditate dolorem expedita molestiae molestias nam nemo nulla ' ||
        'odio pariatur perspiciatis provident quae quod recusandae repudiandae saepe tenetur totam vero voluptatem! ' ||
        'Aliquam asperiores dolor necessitatibus nemo nostrum! Aut corporis nesciunt sunt! Deleniti magnam nemo quos ' ||
        'veritatis! Adipisci earum esse inventore quam sed totam! A ab aut blanditiis consequuntur debitis dolor dolore, ' ||
        'et facere fugiat fugit in ipsa itaque libero magnam maiores molestias nihil non, perspiciatis quasi quo quos ' ||
        'recusandae rerum sit unde vero. Aspernatur consectetur consequatur cumque, dignissimos dolor fugit ipsa modi neque ' ||
        'nihil non odit omnis quaerat quis totam unde vitae voluptates voluptatibus! Accusamus cum dolore esse id illo ' ||
        'molestias nostrum quia!',
        TIMESTAMP '2020-11-08 20:04:00.000000', 'Temporibus tenetur veritatis', news_sequence.nextval);

insert into news_table (news_brief, news_content, news_date, news_title, news_id)
values ('A ab aut blanditiis consequuntur debitis dolor dolore, ' ||
        'et facere fugiat fugit in ipsa itaque libero magnam maiores molestias nihil non, perspiciatis quasi quo quos ' ||
        'recusandae rerum sit unde vero. Aspernatur consectetur consequatur cumque,',
        'Eius explicabo facere fugiat hic, illum obcaecati quia quibusdam, quidem quod rem rerum! Ad aliquid architecto ' ||
        'eius eligendi, enim explicabo ipsa, iste laboriosam odio possimus quas quos soluta unde. Aliquam, consectetur ' ||
        'consequatur cumque deserunt dolorem exercitationem expedita facere iste itaque labore numquam quibusdam quis, ' ||
        'quisquam sunt voluptatum! Accusamus aspernatur beatae commodi dolorem id, incidunt, laboriosam molestiae numquam ' ||
        'repellendus soluta tempore voluptates. Ab dignissimos, dolorum error est et excepturi explicabo inventore magnam ' ||
        'maxime non officia porro reiciendis. Animi culpa cupiditate eos maxime minima molestias, neque non ratione ' ||
        'repudiandae rerum! Aliquid assumenda corporis, cupiditate dolorem expedita molestiae molestias nam nemo nulla ' ||
        'odio pariatur perspiciatis provident quae quod recusandae repudiandae saepe tenetur totam vero voluptatem! ' ||
        'Aliquam asperiores dolor necessitatibus nemo nostrum! Aut corporis nesciunt sunt! Deleniti magnam nemo quos ' ||
        'veritatis! Adipisci earum esse inventore quam sed totam! A ab aut blanditiis consequuntur debitis dolor dolore, ' ||
        'et facere fugiat fugit in ipsa itaque libero magnam maiores molestias nihil non, perspiciatis quasi quo quos ' ||
        'recusandae rerum sit unde vero. Aspernatur consectetur consequatur cumque, dignissimos dolor fugit ipsa modi neque ' ||
        'nihil non odit omnis quaerat quis totam unde vitae voluptates voluptatibus! Accusamus cum dolore esse id illo ' ||
        'velit voluptates voluptatum? Accusantium culpa exercitationem mollitia, odio qui soluta. A aperiam aspernatur',
        TIMESTAMP '2020-11-08 20:05:00.000000', 'Assumenda at ex incidunt', news_sequence.nextval);

insert into news_table (news_brief, news_content, news_date, news_title, news_id)
values ('Dolorem exercitationem expedita facere iste itaque labore numquam quibusdam quis, ' ||
        'quisquam sunt voluptatum! Accusamus aspernatur beatae commodi dolorem id, incidunt, laboriosam molestiae numquam ' ||
        'repellendus soluta tempore voluptates.',
        'Eius eligendi, enim explicabo ipsa, iste laboriosam odio possimus quas quos soluta unde. Aliquam, consectetur ' ||
        'consequatur cumque deserunt dolorem exercitationem expedita facere iste itaque labore numquam quibusdam quis, ' ||
        'quisquam sunt voluptatum! Accusamus aspernatur beatae commodi dolorem id, incidunt, laboriosam molestiae numquam ' ||
        'repellendus soluta tempore voluptates. Ab dignissimos, dolorum error est et excepturi explicabo inventore magnam ' ||
        'maxime non officia porro reiciendis. Animi culpa cupiditate eos maxime minima molestias, neque non ratione ' ||
        'repudiandae rerum! Aliquid assumenda corporis, cupiditate dolorem expedita molestiae molestias nam nemo nulla ' ||
        'odio pariatur perspiciatis provident quae quod recusandae repudiandae saepe tenetur totam vero voluptatem! ' ||
        'Aliquam asperiores dolor necessitatibus nemo nostrum! Aut corporis nesciunt sunt! Deleniti magnam nemo quos ' ||
        'veritatis! Adipisci earum esse inventore quam sed totam! A ab aut blanditiis consequuntur debitis dolor dolore, ' ||
        'et facere fugiat fugit in ipsa itaque libero magnam maiores molestias nihil non, perspiciatis quasi quo quos ' ||
        'recusandae rerum sit unde vero. Aspernatur consectetur consequatur cumque, dignissimos dolor fugit ipsa modi neque ' ||
        'nihil non odit omnis quaerat quis totam unde vitae voluptates voluptatibus! Accusamus cum dolore esse id illo ' ||
        'velit voluptates voluptatum? Accusantium culpa exercitationem mollitia, odio qui soluta. A aperiam aspernatur' ||
        'explicabo facere fugiat hic, illum obcaecati quia quibusdam, quidem quod rem rerum! Ad aliquid architecto ',
        TIMESTAMP '2020-11-08 20:06:00.000000', 'Alias aperiam assumenda', news_sequence.nextval);

insert into news_table (news_brief, news_content, news_date, news_title, news_id)
values ('Ducimus ea eius eligendi esse ex exercitationem, fuga hic, illum ipsam iste. Adipisci earum esse inventore quam' ||
        ' sed totam! A ab aut blanditiis consequuntur debitis dolor dolore, et facere fugiat fugit in ipsa itaque libero',
        'Consequatur cumque deserunt dolorem exercitationem expedita facere iste itaque labore numquam quibusdam quis, ' ||
        'quisquam sunt voluptatum! Accusamus aspernatur beatae commodi dolorem id, incidunt, laboriosam molestiae numquam ' ||
        'repellendus soluta tempore voluptates. Ab dignissimos, dolorum error est et excepturi explicabo inventore magnam ' ||
        'maxime non officia porro reiciendis. Animi culpa cupiditate eos maxime minima molestias, neque non ratione ' ||
        'repudiandae rerum! Aliquid assumenda corporis, cupiditate dolorem expedita molestiae molestias nam nemo nulla ' ||
        'odio pariatur perspiciatis provident quae quod recusandae repudiandae saepe tenetur totam vero voluptatem! ' ||
        'Aliquam asperiores dolor necessitatibus nemo nostrum! Aut corporis nesciunt sunt! Deleniti magnam nemo quos ' ||
        'veritatis! Adipisci earum esse inventore quam sed totam! A ab aut blanditiis consequuntur debitis dolor dolore, ' ||
        'et facere fugiat fugit in ipsa itaque libero magnam maiores molestias nihil non, perspiciatis quasi quo quos ' ||
        'recusandae rerum sit unde vero. Aspernatur consectetur consequatur cumque, dignissimos dolor fugit ipsa modi neque ' ||
        'nihil non odit omnis quaerat quis totam unde vitae voluptates voluptatibus! Accusamus cum dolore esse id illo ' ||
        'velit voluptates voluptatum? Accusantium culpa exercitationem mollitia, odio qui soluta. A aperiam aspernatur' ||
        'explicabo facere fugiat hic, illum obcaecati quia quibusdam, quidem quod rem rerum! Ad aliquid architecto ' ||
        'Eius eligendi, enim explicabo ipsa, iste laboriosam odio possimus quas quos soluta unde. Aliquam, consectetur.',
        TIMESTAMP '2020-11-08 20:07:00.000000', 'Eius eligendi esse', news_sequence.nextval);

insert into news_table (news_brief, news_content, news_date, news_title, news_id)
values ('in ipsa itaque libero magnam maiores molestias nihil non, perspiciatis quasi quo quos ' ||
        'recusandae rerum sit unde vero. Aspernatur consectetur consequatur cumque, dignissimos dolor fugit ipsa modi neque ' ||
        'nihil non odit omnis quaerat quis totam!',
        'Quisquam sunt voluptatum! Accusamus aspernatur beatae commodi dolorem id, incidunt, laboriosam molestiae numquam ' ||
        'repellendus soluta tempore voluptates. Ab dignissimos, dolorum error est et excepturi explicabo inventore magnam ' ||
        'maxime non officia porro reiciendis. Animi culpa cupiditate eos maxime minima molestias, neque non ratione ' ||
        'repudiandae rerum! Aliquid assumenda corporis, cupiditate dolorem expedita molestiae molestias nam nemo nulla ' ||
        'odio pariatur perspiciatis provident quae quod recusandae repudiandae saepe tenetur totam vero voluptatem! ' ||
        'Aliquam asperiores dolor necessitatibus nemo nostrum! Aut corporis nesciunt sunt! Deleniti magnam nemo quos ' ||
        'veritatis! Adipisci earum esse inventore quam sed totam! A ab aut blanditiis consequuntur debitis dolor dolore, ' ||
        'et facere fugiat fugit in ipsa itaque libero magnam maiores molestias nihil non, perspiciatis quasi quo quos ' ||
        'recusandae rerum sit unde vero. Aspernatur consectetur consequatur cumque, dignissimos dolor fugit ipsa modi neque ' ||
        'nihil non odit omnis quaerat quis totam unde vitae voluptates voluptatibus! Accusamus cum dolore esse id illo ' ||
        'velit voluptates voluptatum? Accusantium culpa exercitationem mollitia, odio qui soluta. A aperiam aspernatur' ||
        'explicabo facere fugiat hic, illum obcaecati quia quibusdam, quidem quod rem rerum! Ad aliquid architecto ' ||
        'Eius eligendi, enim explicabo ipsa, iste laboriosam odio possimus quas quos soluta unde. Aliquam, consectetur.' ||
        'Consequatur cumque deserunt dolorem exercitationem expedita facere iste itaque labore numquam quibusdam quis.',
        TIMESTAMP '2020-11-08 20:08:00.000000', 'Nobis omnis pariatur', news_sequence.nextval);