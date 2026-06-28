# Authoring Guide

Matheus' Brew is a hand-curated newsletter built with [Quarkus Roq](https://iamroq.dev). Editions are pure YAML files — no HTML, no markdown, no template syntax.

## Quick Start

Create a new file at `data/brews/YYYY-MM-DD.yml`:

```yaml
title: "Edition #03: Your Title Here"
description: "One-line summary of what is in this edition."
date: 2026-08-01
tags: [tag1, tag2, tag3]
issue: 3
link: /brews/2026/08/01/
layout: brew-post

sections:
  - name: "Highlight"
    type: "highlight"
    badge: "Must Read"
    cards:
      - title: "Article Title"
        url: "https://example.com/article"
        source: "example.com"
        description: "What this article is about."
        comment: "Your personal take on why this matters."
```

Then start the dev server and open `/brews/2026/08/01/`.

## Directory Structure

```
brew.omatheusmesmo.dev/
├── data/
│   └── brews/              # Editions as YAML files
│       └── YYYY-MM-DD.yml
├── content/                # Static pages (homepage, brews listing, tags)
│   ├── index.html
│   ├── brews.html
│   └── tags.html
├── templates/
│   └── layouts/
│       ├── base.html       # Root layout (nav, theme toggle, head)
│       └── brew-post.html  # Brew edition renderer
├── web/
│   └── app.css             # All styles
└── src/main/resources/
    └── application.properties
```

## Section Types

Every section has a `name` (displayed as an `<h2>`) and a `type` that controls rendering:

### `highlight`

One card per section. Requires `badge` at the section level.

| Field | Required | Description |
|-------|----------|-------------|
| `title` | Yes | Card heading |
| `url` | Yes | Link target |
| `source` | No | Short domain shown in the card (falls back to full URL) |
| `description` | Yes | Body text |
| `comment` | No | Personal commentary in a speech bubble |

```yaml
- name: "Highlight"
  type: "highlight"
  badge: "Must Read"
  cards:
    - title: "..."
      url: "https://..."
      source: "example.com"
      description: "..."
      comment: "..."
```

### `link`

Multiple cards per section. No badge. Same card fields as highlight.

```yaml
- name: "Articles"
  type: "link"
  cards:
    - title: "..."
      url: "https://..."
      source: "example.com"
      description: "..."
      comment: "..."   # optional
```

### `nerd`

Used for "Books Insights" and "Nerd Corner". No URL — just a title and text body.

| Field | Required | Description |
|-------|----------|-------------|
| `title` | Yes | Card heading |
| `text` | Yes | Body text (no URL needed) |
| `comment` | No | Personal commentary |

```yaml
- name: "Nerd Corner"
  type: "nerd"
  cards:
    - title: "..."
      text: "..."
      comment: "..."   # optional
```

## Valid Section Names

Sections can have any `name`, but these names have built-in CSS emoji prefixes:

| name | Emoji | CSS selector |
|------|-------|-------------|
| `Highlight` | 🔥 | `data-section="highlight"` |
| `Articles` | 📚 | `data-section="articles"` |
| `Tools` | 🛠 | `data-section="tools"` |
| `Platforms` | ☁️ | `data-section="platforms"` |
| `Languages & Frameworks` | 💻 | `data-section="languages-frameworks"` |
| `Books Insights` | 📖 | `data-section="books-insights"` |
| `Videos` | 🎬 | `data-section="videos"` |
| `Nerd Corner` | 🧠 | `data-section="nerd-corner"` |

Custom section names work but will not have emoji prefixes. Add them to `web/app.css` if needed.

## Configuration

Key settings in `application.properties`:

```properties
site.collections.brews.from-data.id-key=_key   # reads from data/brews/
site.collections.brews.layout=brew-post         # renders with brew-post.html
```

The `_key` is the filename without extension. The `link` field in the YAML frontmatter sets the page URL.

## Local Development

```bash
./mvnw quarkus:dev
```

The site serves at `http://localhost:8080`. Changes to YAML files and layouts are picked up by hot reload.

## Build-Time Validation

Section `type` must be one of: `highlight`, `link`, `nerd`. Using an invalid type will render the card without styling (it falls through to the default `brew-card` class). This is intentional — it shows the content but makes the mistake visible.

Adding new CSS emoji prefixes for custom section names is done in `web/app.css`:

```css
h2[data-section="my-custom-section"]::before { content: "✨ "; }
```
