#!/usr/bin/python
#coding:utf-8


import re
import urllib
import sys
from mechanize import Browser
from bs4 import BeautifulSoup


br = Browser()

searchWord = sys.argv[1]

# Ignore robots.txt
br.set_handle_robots( False )
# Google demands a user-agent that isn't a robot
# br.addheaders = [('User-agent', 'Firefox')]
br.addheaders = [('User-agent', 'Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.1) Gecko/2008071615 Fedora/3.0.1-1.fc9 Firefox/3.0.1')]


# Retrieve the Google home page, saving the response
br.open("http://google.com")

# Select the search box and search for 'foo'
br.select_form('f')
br.form['q'] = searchWord + str('thesaurus.com')

# Get the search results
br.submit()

# Find the link to foofighters.com; why did we run a search?
resp = None
for link in br.links():
    siteMatch = re.compile('www.thesaurus.com').search(link.url)
    if siteMatch:
		resp = br.follow_link(link)
		soup = BeautifulSoup(resp, "html.parser")
		relevancy_list = soup.find_all("div", class_="relevancy-list")
		if len(relevancy_list) > 0:
			ul = relevancy_list[0].find_all("ul")
			synonyms = ul[0].find_all("span", class_="text")
			for element in synonyms:
				print element
				print "\r\n\r\n"
			synonyms = ul[1].find_all("span", class_="text")
			for element in synonyms:
				print element
				print "\r\n\r\n"
			break

