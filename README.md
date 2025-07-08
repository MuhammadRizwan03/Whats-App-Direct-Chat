# Whats-App-Direct-Chat

# 🌍 Country Picker Android App

This Android app displays a list of countries with their flags, names, and phone codes using a local JSON file stored in the `assets/` folder. The flags are loaded from SVG URLs using **Coil** with SVG support.

---

## 🚀 Features

- Loads country data from `countries.json` in `assets/`
- Shows country name, ISO code, and phone code
- Loads SVG flag images using **Coil**
- Uses **cache-aware singleton ImageLoader** for performance
- Modern code written in **Kotlin**
- RecyclerView with ViewBinding support

---

## 📦 JSON Format (assets/countries.json)

```json
[
  {
    "name": "Andorra",
    "english_name": "Andorra",
    "name_code": "ad",
    "phone_code": "376",
    "flag_url": "https://cdn.jsdelivr.net/gh/hampusborgos/country-flags@main/svg/ad.svg"
  }
]
```
## 💬 Direct Chat Feature (WhatsApp Integration)

This app includes a powerful **Direct Chat** feature that enables users to start a WhatsApp or WhatsApp Business conversation **without saving the number** to contacts.

---

### ✨ Highlights

- 📲 Input phone number with or without formatting (`+92 300-1234567`, `03001234567`, etc.)
- 🌍 Country ISD codes auto-selected from a JSON-backed country list
- 🛡️ Input is sanitized using Regex to prevent malformed or invalid numbers
- 🧠 Uses `applicationContext` via a custom `MyApplication` class to **avoid memory leaks**
- 💬 Optional message support sent directly to WhatsApp chat
- 🔀 Supports both regular WhatsApp and WhatsApp Business using Intent resolution