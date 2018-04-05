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
br.open("http://www.google.com")

# Select the search box and search for the word
br.select_form('f')
br.form['q'] = searchWord + str('yourdictionary')

# Get the search results
br.submit()

# Find the definition
resp = None
for link in br.links():
    siteMatch = re.compile('www.yourdictionary.com').search(link.url)
    if siteMatch:
		resp = br.follow_link(link)
		soup = BeautifulSoup(resp, "html.parser")
		definitions = soup.find_all("div", class_="custom_entry")
		for element in definitions:
			print element
			print "\n\n"
		break

