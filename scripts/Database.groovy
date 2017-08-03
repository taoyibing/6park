@GrabConfig(systemClassLoader=true)
@Grab(group='org.postgresql', module='postgresql', version='9.4.1210')
import groovy.sql.Sql;

class Database {

	def Sql sql;
	Database(host, databaseName, user, password){
		sql = Sql.newInstance("jdbc:postgresql://$host/$databaseName", user, password, "org.postgresql.Driver")
	}

	def getSite(url){
		def row = sql.firstRow("select id from sites where url=$url")
		if (row != null)	
			return row.id
		
		sql.executeInsert("INSERT INTO sites (url) VALUES ($url)")
		return sql.firstRow("select id from sites where url=$url").id
	}

	def addLink(siteId,name,link){
		try{
			sql.executeInsert("INSERT INTO links (site_id,name,link) VALUES ($siteId,$name,$link)")	
		}catch(org.postgresql.util.PSQLException ex){
			println ex
		}
	}

	def getLinks(siteId){
		return sql.rows("""select links.id, links.name, sites.url,links.link 
			from links,sites 
			where links.site_id=$siteId and links.site_id=sites.id order by links.name ;""")
	}

	def getLinks(siteId,link_name){
		def param = "%$link_name%".toString()
		return sql.rows("""select links.id, links.name, sites.url,links.link 
			from links,sites 
			where links.site_id=$siteId and links.site_id=sites.id and links.name like ? ;""", [param])		
	}

	def updateLink(id,name){
		sql.executeUpdate("UPDATE links SET name=$name WHERE id=$id")
	}

}