
def urlRoot = "http://www.cool18.com/bbs4/"
def db = new Database('localhost','mydb','test','test')
def siteId = db.getSite(urlRoot)
def rows = db.getLinks(siteId)

def Normalize(cc) {
	def str = ''
	for(int i=0;i<cc.length();i++){
		def ch = cc.charAt(i)
		if( ch != '【' && ch != '】'
			&& ch != '《' && ch !='》'
			&& ch !='★' && ch !='●'
			&& ch != '〖' && ch !='〗') {
			str += ch
		}
	}
	return str
}

rows.each{ row ->
	println Normalize(row.name)
	db.updateLink(row.id , Normalize(row.name))
}