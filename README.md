# Matheus' Brew

A hand-curated tech newsletter. No algorithms, no scraping, no automation: every link is read, filtered, and annotated by a human before it reaches your inbox.

Built with [Quarkus Roq](https://github.com/quarkiverse/quarkus-roq).

## What makes it different

The Brew is not an aggregator. It is a manual curation process: I read articles, test tools, watch talks, and take notes on books throughout the week. What makes the cut is what I would actually send to a friend. One person's taste, one newsletter.

## Sections

- **Highlight**: the most important link of the edition
- **Articles**: blog posts, deep dives, and technical reads
- **Tools**: developer tools worth trying or watching
- **Platforms**: infrastructure, cloud services, and platforms
- **Languages & Frameworks**: programming languages, frameworks, and libraries
- **Videos**: talks, conference recordings, and video content
- **Books Insights**: useful ideas pulled from technical books
- **Nerd Corner**: curiosities, deep cuts, and technical rabbit holes

## Local Development

```bash
# Start development server with hot reload
./mvnw quarkus dev
```

## Content Structure

```
content/
  brews/
    YYYY-MM-DD-slug/
      index.md            # Brew edition (Markdown + HTML)
```

## Creating a New Edition

```bash
mkdir -p "content/brews/$(date +%Y-%m-%d)-my-slug"
```

Frontmatter example:

```yaml
title: "Edition #01: ..."
description: "..."
date: 2026-07-01
tags: [java, ai, rust, debugging]
issue: 1
link: /brews/2026/07/01/
```

## Deployment

The site deploys to GitHub Pages. Newsletters are sent via MailerLite using `scripts/send-newsletter.js`.

## License

MIT
