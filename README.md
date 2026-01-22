IAMU - News Android App
Android aplikacija za pregled vijesti s podrškom za offline spremanje, notifikacije i multijezični prikaz (Hrvatski/English). Koristi Retrofit za dohvaćanje vijesti iz API-ja i Room za lokalno spremanje.

📱 O projektu
News aplikacija omogućava korisnicima pregled najnovijih vijesti, spremanje članaka offline, postavljanje notifikacija i prilagodbu postavki (dark mode, jezik). Koristi moderne Android komponente kao što su Navigation, WorkManager i Content Provider.

🛠 Tehnologije
Kotlin - Programski jezik

Android SDK 36 (min SDK 26 - Android 8.0)

Gradle (Kotlin DSL) - Build sistem

Retrofit 2 - REST API komunikacija

Gson - JSON parsing

Picasso - Image loading i caching

WorkManager - Background tasks (periodic news fetching)

Navigation Component - Fragment navigacija

View Binding - Type-safe view access

Content Provider - Data sharing

Broadcast Receiver - Event handling

📦 Preduvjeti
Android Studio (Ladybug ili noviji)

JDK 11

Android SDK 36

Android emulator ili fizički uređaj (Android 8.0+)

🚀 Instalacija i pokretanje
1. Kloniraj projekt
bash
git clone <repository-url>
cd IAMU
2. Otvori u Android Studio
File → Open → Odaberi IAMU folder

Pričekaj Gradle sync

3. Konfiguriraj API
Uredi NewsApi.kt i postavi API endpoint ako je potrebno.

4. Pokreni aplikaciju
Odaberi emulator ili uređaj

Run → Run 'app' (ili Shift + F10)

⚡ Glavne funkcionalnosti
Splash Screen
Animirani logo pri pokretanju

Automatski prelazak na glavni ekran

Lista vijesti (ItemsFragment)
Prikaz vijesti iz API-ja

Pull-to-refresh

RecyclerView s adapter pattern

Klik na vijest → prelazak na detalj

Detalj vijesti (DetailFragment)
Puni tekst članka

Slika vijesti (Picasso)

Opcija za brisanje vijesti

Postavke (SettingsFragment)
Dark Mode - Prebacivanje na tamnu temu

Jezik - Hrvatski / English (multijezična podrška)

Spremanje postavki u SharedPreferences

Background sync (NewsWorker)
WorkManager - Periodično dohvaćanje novih vijesti

Radi u pozadini čak i kad je app zatvoren

Notifikacije za nove vijesti

Offline podrška
Content Provider - Lokalno spremanje vijesti

Pristup vijestima bez interneta

🌐 Multilingual
Podržani jezici:

English (default)

Hrvatski (values-hr/strings.xml)

Automatski odabir prema sustavu ili ručno u postavkama.

🎨 UI Features
Navigation Drawer - Meni za navigaciju

Animacije - Slide, fade transitions

Dark Theme - Material Design dark theme

Custom icons - Svi ikoni prilagođeni

📡 Dozvole
xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
