/**
  First we insert some servers. One will be diplo and 2 will be archived(1 diplo and 1 normal)
 */

INSERT INTO server
(unique_name,
 name,
 dir,
 num_players,
 archived,
 diplo)
  VALUES
  ('lvc_01',
   'livecon 1v1 default',
   'livecon_1v1_default',
   2,
   0,
   0);


INSERT INTO server
(unique_name,
 name,
 dir,
 num_players,
 archived,
 diplo)
  VALUES
  ('lvc_02',
   'livecon 2v2 default',
   'livecon_2v2_default',
   4,
   0,
   0);


INSERT INTO server
(unique_name,
 name,
 dir,
 num_players,
 archived,
 diplo)
  VALUES
  ('lvc_03',
   'livecon 3v3 default',
   'livecon_3v3_default',
   6,
   0,
   0);


INSERT INTO server
(unique_name,
 name,
 dir,
 num_players,
 archived,
 diplo)
  VALUES
  ('lvc_04',
   'livecon premium diplo',
   'livecon_premium_diplo',
   6,
   0,
   1);


INSERT INTO server
(unique_name,
 name,
 dir,
 num_players,
 archived,
 diplo)
  VALUES
  ('lvc_05',
   'livecon arch 1v1',
   'livecon_arch_1v1',
   2,
   1,
   0);


INSERT INTO server
(unique_name,
 name,
 dir,
 num_players,
 archived,
 diplo)
  VALUES
  ('lvc_06',
   'livecon arch diplo',
   'livecon_arch_diplo',
   6,
   1,
   1);

/**
  Then we insert games. One 1v1 and one 2v2.
 */

INSERT INTO game (server_id, date_start, date_end)
  VALUES (1, current_date(), current_date() + 1);

INSERT INTO game (server_id, date_start, date_end)
  VALUES (2, current_date() + 1, current_date() + 2);

/**
   Then we add drecs to them.
 */

INSERT INTO dcrec (game_id, dir, full_path)
  VALUES (1, 'livecon_1v1_default/archive/2013/02/', 'livecon_1v1_default/archive/2013/02/13:23:04.dcrec');

INSERT INTO dcrec (game_id, dir, full_path)
  VALUES (2, 'livecon_2v2_default/archive/2013/02/', 'livecon_2v2_default/archive/2013/02/13:23:04.dcrec');

/**
  After that we insert 4 players (all noobs :))
 */

INSERT INTO player (keyId) VALUES ('noob-00-1');

INSERT INTO player (keyId) VALUES ('noob-00-2');

INSERT INTO player (keyId) VALUES ('noob-00-3');

INSERT INTO player (keyId) VALUES ('noob-00-4');

/**
  We insert the associative date - attaching players to games. 2 for the 1v1 and 4 for the 2v2.
  The first 2 played both in the 1v1 and in the 2v2. Still noobs though :).
 */

INSERT INTO game_has_player (game_id, player_id, territory_id, ip, score)
  VALUES (1, 1, 1, '111.111.111', 11);

INSERT INTO game_has_player (game_id, player_id, territory_id, ip, score)
  VALUES (1, 2, 2, '222.222.222', 22);

INSERT INTO game_has_player (game_id, player_id, territory_id, ip, score)
  VALUES (2, 1, 1, '111.111.222', 33);

INSERT INTO game_has_player (game_id, player_id, territory_id, ip, score)
  VALUES (2, 2, 2, '222.222.333', 44);

INSERT INTO game_has_player (game_id, player_id, territory_id, ip, score)
  VALUES (2, 3, 3, '333.333.333', 55);

INSERT INTO game_has_player (game_id, player_id, territory_id, ip, score)
  VALUES (2, 4, 4, '444.444.444', 66);


/**
  We add team information. In the 2v2, 2 player switched teams.
 */
INSERT INTO game_has_player_has_team (game_has_player_id, team_id, orderdir)
  VALUES (1, 1, 1);

INSERT INTO game_has_player_has_team (game_has_player_id, team_id, orderdir)
  VALUES (2, 2, 1);

INSERT INTO game_has_player_has_team (game_has_player_id, team_id, orderdir)
  VALUES (3, 1, 1);

INSERT INTO game_has_player_has_team (game_has_player_id, team_id, orderdir)
  VALUES (4, 2, 1);

INSERT INTO game_has_player_has_team (game_has_player_id, team_id, orderdir)
  VALUES (5, 3, 1);

INSERT INTO game_has_player_has_team (game_has_player_id, team_id, orderdir)
  VALUES (6, 4, 1);

INSERT INTO game_has_player_has_team (game_has_player_id, team_id, orderdir)
  VALUES (5, 4, 2);

INSERT INTO game_has_player_has_team (game_has_player_id, team_id, orderdir)
  VALUES (6, 3, 2);


/**
   We add some in-game names to the players. One in-game name for each except the last player who changed names.
   He was actually Endless in disguise. So even more noob :).
 */

INSERT INTO ingame_name (game_has_player_id, name, orderDir)
  VALUES (1, 'nooblet 1', 1);

INSERT INTO ingame_name (game_has_player_id, name, orderDir)
  VALUES (2, 'nooblet 2', 1);

INSERT INTO ingame_name (game_has_player_id, name, orderDir)
  VALUES (3, 'nooblet 1', 1);

INSERT INTO ingame_name (game_has_player_id, name, orderDir)
  VALUES (4, 'nooblet 2', 1);

INSERT INTO ingame_name (game_has_player_id, name, orderDir)
  VALUES (5, 'nooblet 3', 1);

INSERT INTO ingame_name (game_has_player_id, name, orderDir)
  VALUES (6, 'nooblet 4', 1);

INSERT INTO ingame_name (game_has_player_id, name, orderDir)
  VALUES (6, 'Endless', 2);