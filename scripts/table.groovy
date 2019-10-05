import groovy.xml.MarkupBuilder

def urlRoot = "http://www.cool18.com/bbs4/"
def db = new Database('localhost','mydb','test','test')
def siteId = db.getSite(urlRoot)

//args: filter output colcount
//exmaple abc index1.html 1

def rows 
def file 
def colCount=3
if(args.length == 0){
	file = new File('index.html')
	rows = db.getLinks(siteId)
}
else if(args.length == 1){
	rows = db.getLinks(siteId,args[0])
	file = new File('index.html')
}
else if(args.length == 2)
{
	rows = db.getLinks(siteId,args[0])
	file = new File(args[1])	
}
else if (args.length == 3){
	colCount = args[2].toInteger()
	rows = db.getLinks(siteId,args[0])
	file = new File(args[1])	
}

file.withWriter('gbk') { writer ->
	def html = new MarkupBuilder(writer)

	html.doubleQuotes = true
	html.expandEmptyElements = true
	html.omitEmptyAttributes = false
	html.omitNullAttributes = false
	html.html {
	    head {
	        style { 
	        	mkp.yield('''table { width: 100%}
							table, th, td {
							    border: 1px solid black;
							    border-collapse: collapse;
							}
	        		''')
	        }
	    }
	    body {
		    table {
		    	
		    	for (int i=0;i<=rows.size()/colCount;i++){
		    		tr{
		    			for(int j=0;j<colCount;j++){
		    				if(i*colCount+j<rows.size()){
			    				row = rows[i*colCount+j]
			    				td {
			    					a (target:"_blank", href: row.url+row.link) { 
			    						mkp.yield(row.name)
			    					} 
			    				}
		    				}
		    			}
		    		}
		    	}
		    }
	    }
	}
}
