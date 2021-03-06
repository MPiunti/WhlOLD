

--###########################################################
--  USERS
--###########################################################
-- All the documentes owned by some username//by index is 'named'
START u=node:username(login = "m.piunti")  
MATCH u-[:OWNS]->d
return u.login, d.name

--###########################################################
--  MUSEUMS
--###########################################################
START d=node:docname(name = "museums")   
MATCH d-[:INCLUDES]->r-[:LOCATED]->venue
RETURN d.name as document_name, r.row as row_list, venue.wkt

--###########################################################
--  ARTISTS
--#############################################################
START d=node:docname(name = "artists")   
MATCH d-[:INCLUDES]->r-[:DBP_LINKED]->dbp
RETURN d.name as document_name, r.row as row_list, dbp.URI
----
--- ALL THE DBPedia URIS in a DOCUMENT
START d=node:docname(name = "artisti")    
MATCH d-[:INCLUDES]->r-[:DBP_LINKED]->l
RETURN l, l.URI





-- Form USERS to document ROWS
START u=node:username(login = "m.piunti")  
MATCH u-[:OWNS]->d-[:INCLUDES]->r
return u.login, d.name, r

--  all the DOCUMENTS owned by a certain username

START d = node(*)
MATCH u-[:OWNS]->d
WHERE u.login = 'm.piunti'
RETURN u.login as username, d.name as title, d as node



--  all the ROWS of a certain document_name --by index is 'named'

START d=node:docname(name = "luoghi")   
MATCH d-[:INCLUDES]->r
//WHERE d.name = 'luoghi'
RETURN d.name as document_name, r.row as row_list

/*START d = node(*)
MATCH d-[:INCLUDES]->r
//WHERE d.name = 'luoghi'
RETURN d.name as document_name, r.row as row_list*/


----
--- ALL THE LOCATIONS in a DOCUMENT
START d=node:docname(name = "venue")    
MATCH d-[:INCLUDES]->r-[:LOCATED]->l
RETURN r,r.row,l.wkt

----------------------------------
-- ##### DELETE
----------------------------------
START n=node(*)
//MATCH (n)-[r]-()
//WHERE n.type="OPEN_DOCUMENT" or n.type="OPEN_NODE" or n.type="USER"
DELETE n