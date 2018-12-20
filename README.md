# ASE DAM Project

## App Concept
### Conference booking app

#### How to get this Project
Make sure you have Git installed and, in a folder of your preference, run the following in Git Bash:
```bash
git clone https://github.com/codepadawan93/ase-dam-project.git
```
Afterwards open the folder with Android Studio. Remotes should be already set up for you. You can commit and push using the AS built-in functionality in the toolbar: VCS -> Git -> Commit and Push buttons if you don't want to use the CLI. 

If your push is rejected, pull latest changes first and try to push again. If you find conflicts, make a backup of your code in a separate file (just in case) and accept the incoming changes. Test if project compiles and take a note of the latest commit before pushing.

#### User roles
- Professor (provider)
- Student (consumer)

#### Entities
- User - create account, edit/view/delete account, list view account
  - user_id
  - user_name
  - password
  - firstname
  - lastname
  - role
 
- Questionnaire - Professor create/edit/view/delete Questionnaire, list view own questionnaires
    - questionnaire_id
    - title
    - nr_of_questions [EK] 
    - date_start
    - date_end (if they are empty it's always available)
    - hash_code (unique code through which users can access questionnaire)
    - public (true/false)

- Question - Professor create/edit/view/delete by professor when creating Questionnaire
     - question_id
     - title
     - text
     - type (single answer / multiple answer / freeform answer)
     - [EK] remains to be established: will Questions be bound to a Questionnaire, or will we make a m2m table?
     - [AF] nu stiu ce caracteristici ar putea sa aiba in mod deosebt pentru ca practic in questionnaire vom avea un arraylist de intrebari 

- Response - created when a Questionnaire is submitted
     - response_id
     - questionnaire_id
     - completed_on
     - nr_of_correct_answ [EK] not sufficient to remeber the score, we should remember the whole playthrough
     - answers (a LONGTEXT field cotaining a JSON with the playthrough data)
     - user_id 
     //[AF] ?? acesta  va fi folosit pentru history 
       
#### Database
![Database](https://codepadawan93.github.io/assets/dam-ase/database-schema.jpg)

#### Views
- Sign up -[AF]
- Sign in -[AF]
- Main view -[EK]
- Edit/View/Delete User Account form -[AF]
- List view User Accounts - only Professor can see -[EK]
- Add/Edit/View/Delete Questionnaire form - only for Professor -[EK]
- List view all Questionnaires - only for Professor - [EK]
- Add/Edit/View/Delete Question form - only Professor can see -[RG]
- List view all unassigned Questions (all questions that are not associated to a parent Questionnaire) - only for Professor -[RG]
- History - for Student a list of his responses, for Professor a list of all responses grouped by Questionnaire -[RG]
- Question View - only for Student (main game loop)
- Settings -[AG]
- Contact -[AG]
- About -[AG]
- Help [AG]

#### Possible actions
- Student/Professor can create own account
- Only one account per device
- Create Questionnaire with multiple Questions having multiple Answers each
- Question can have one or multiple Answers, and also a max response time, display current score, display feedback if wrong answer was provided, and an attached picture from URL 
- A student will be able to acces a Questionaire on basis of a Code given by Professor
- A Professor will be able to generate a Code providing the Questionnaire he wants to become public and the interval in which itt can be filled out. 
- A flag will control whether a Questionnaire will be traversable at will (a user can go back to previous questions) or only in one way
- Student user can log in to any test if he has the proper code provided by Professor
- Questions are associated to a Category
- Professor must be able to view all instances of a Questionnaire and the Student that participated
- Professor can create a Team
- Each Student can be assigned by a Professor to be part of a Team

### Ideas - Adela
MainActivity:
Menu with 
– Create Account
-	Log in
-	History ( questionnaire and scores for each student) 
-	Settings
-	Contact
-	About
-	Help

-Activities :                       
 - create questionnaire + assign it to a category 
 -	Activity for setting the max response time, display the score and feedback for each questionnaire 
 -	Enter test :  field for the code (if student) 
 -	??? how and where do we generate the codes
  -- [EK] A user of type Professor will receive a hash specific to a questionnaire once he has created it. It will be bound to that specific instance of the questionnaire - if he changes anything, a new code will be generated. In the view where he can edit the questionnaire there will be a field with this code and he can get it from there if he needs it after he created the questionnaire;
 -	??? we need a new activity for each question of a questionnaire or better  use fragments 
  -- [EK] There will only ever be one view, and it will be populated dynamically with different data for each question. We will not create them manually, only the logic behind the display of each field. I am not sure how can this be achieved in Android though, needs more investigation;
 -	Create teams 


## Barem
### Faza 1: (2.5 p)
1. Definirea a minim patru activități/membru echipă; (0.5 p.)
2. Utilizarea de controale variate (Button, TextView, EditView, CheckBox, Spinner, ProgressBar, SeekBar, Switch, RatingBar, ImageView, DatePicker sau TimePicker); (1 p.)
3. Utilizarea a minim un formular de introducere a datelor/membru; (0.5 p.)
4. Transferul de parametri (obiecte proprii și primitive) între minim două activități; (0.5 p.)
5. Toate activitățile aplicației trebuie populate folosind controale vizuale corespunzătoare.

### Faza 2: (2.5 p)
1. Implementarea unui adaptor personalizat (cel puțin trei controale vizuale); (0.5 p.)
2. Implementarea și utilizarea unor operații asincrone; (1 p.)
3. Utilizarea claselor pentru accesul la resurse externe (din rețea); (0.5 p.)
4. Prelucrarea de fișiere JSON sau XML. Fișierele trebuie să conțină cel puțin 3 noduri dispuse pe niveluri diferite. Fiecare nod trebuie sa aibă cel puțin 3 atribute; (0.5 p.)

### Faza 3: (2.5 p)
1. Utilizarea fișierelor de preferințe; (0.5 p.)
2. Crearea unei baze de date SQLite cu minim două tabele cu legături intre ele (o tabelă/membru). Implementarea operațiilor de tip DDL; (0.5 p.)
3. Implementarea operațiilor de tip DML. Pentru fiecare tabela sa se implementeze cel puțin o metoda de insert, update, delete si select. Toate metodele trebuie apelate; (0.5 p.)
4. Definirea a minim două rapoarte pe datele stocate în baza de date. Prin raport, se înțelege afișarea pe ecranul dispozitivului mobil a informațiilor preluate din baza de date. Rapoartele sunt diferite ca si structura. (0.5 p.). Componentele vizuale în care se afișează cele doua rapoarte trebuie sa fie diferite de cele prezentate la faza 1 și 2
5. Salvarea rapoartelor în fișiere normale. (txt, csv etc.) (0.5 p.)

### Final: (2.5 p)
1. Utilizarea bazelor de date la distanță (Firebase) (salvare/restaurare); Afișarea informațiilor din Firebase să se realizeze prin intermediul componentelor vizuale (se pot folosi activitățile deja prezentate în fazele anterioare) (0.75 p.)
2. Utilizarea de elemente de grafică bidimensională, cu valori preluate din baza de date (locală sau la distanță); (0.5 p.)
3. Prelucrarea elementelor de tip imagine din diferite surse (imagini statice, preluate prin rețea, încărcate din galeria dispozitivului mobil, preluate din baze de date locală sau la distanță); Imaginile trebuie preluate din minim două surse. (0.5 p.)
4. Stilizarea aplicației mobile (de exemplu, se creează o temă nouă în fișierul styles.xml sau
modificați fontul, culorile componentelor vizuale) (0.5 p)
5. Implementarea unei funcționalități pe bază de Google Maps; (0.25 p.)

### Componente
- Aplicație mobilă pentru platformele Android - faza I
- Bază de date pentru stocarea datelor utilizatorilor, a testelor și a rezultatelor/rapoartelor - faza
II

### Cerințe funcționale
1. Platforma trebuie să fie cât mai ușor de folosit - număr minim de click-uri și input pentru a
participa la teste
2. Platforma trebuie să permită administrarea a două tipuri de utilizatori: studenți și profesori
3. Aplicația trebuie să permită înregistrarea studenților și a profesorilor cu adresele de
email/conturile instituționale
4. Aplicația trebuie să permită înregistrarea unui singur utilizator (aplicația mobilă nu poate fi
folosită de mai mulți utilizatori în același timp pe același dispozitiv)
5. Autentificarea utilizatorilor se va face prin serviciul LDAP al ASE (se va folosit REST API-ul
dezvoltat)
6. Platforma mobilă trebuie sa permită crearea de conținut - întrebări de tip grilă cu
  a. număr limitat de opțiuni
  b. timp maxim de răspuns
  c. punctaj
  d. feedback pentru răspuns greșit
  e. imagine sau video atașat (din sursă locală sau URL)
7. Platforma trebuie să permită evaluarea în timp real a răspunsurilor - cu feedback imediat
pentru profesor și student participant
 8. Platforma trebuie să permită profesorilor să controleze când testul devine activ/inactiv
  a. perioada de vizibilitate a testului
  b. cod de acces la test
V1.0/20181007
9. Platforma trebuie să permită definirea de teste publice (liber accesibile pe platformă) dar și
private (accesul este controlat de profesor). Alte caracteristici pentru un test
  a. amestecarea întrebărilor și a răspunsurilor
  b. numărul de încercări de rulare a testului
  c. furnizare feedback - răspunsul corect sau un text asociat întrebării
  d. afișarea scorului final
  e. alocare timp per test - suprascrie timpul asociat fiecărei întrebări
  f. alocare punctaj unitar pe fiecare întrebare
  g. posibilitatea de a reveni la întrebări anterioare/posibilitatea de a parcurge testul doar într-un sens
10. Platforma trebuie să permită studenților să se înscrie/să participe la un test activ - soluție
recomandată pe baza unui cod dat de profesor
11. Platforma trebuie să permită gestiunea testelor existente
  a. fiecare profesor are un portofoliu de teste și un portofoliu de întrebări ce pot fi clasificate pe diferite categorii (într-o prima fază se poate merge pe o soluție în care întrebările sunt asociate direct unui test)
  b. profesorii pot partaja teste între ei cu diferite drepturi (utilizare și/sau editare)
12. Platforma trebuie să permită gestiunea istoricului unui student: când a dat un test și ce punctaj a obținut
13. Platforma trebuie să genereze rapoarte pentru profesor
  a. lista studenților care au dat un test și punctajul acestora
  b. lista răspunsurilor date de un student pentru un test
14. Platforma trebuie să permită interacțiunea între studenți, acești putând fi incluși în echipe
(generate aleatoriu sau după alte criterii)
15. Platforma trebuie să permită crearea de întrebări diferite (de tip grilă cu răspuns unic sau
multiplu - obligatoriu, cu răspuns deschis)

### Depunctări
- 2 puncte - nerespectarea cerințelor de predare a proiectului (mod de trimitere a fișierelor,
denumire arhivă, structură arhivă, neștergerea directoarelor build).
- 2 puncte – lipsa documentației sau nerespectarea conform cerințelor de elaborare conform
structurii
- toate șirurile de caractere utilizate la nivelul interfeței trebuie preluate din resurse. Lipsa
acestora duce la o penalizare de 0.5 din fiecare faza.


## Project
### Faza 1: (2.5 p)
1. Definirea a minim patru activități/membru echipă; - 14 activities (good enough)
2. Utilizarea de controale variate:
- [x] Button
- [x] TextView
- [x] EditView
- [x] CheckBox
- [x] Spinner
- [ ] ProgressBar
- [ ] SeekBar
- [x] Switch
- [ ] RatingBar - pending Raluca
- [x] ImageView
- [x] DatePicker sau TimePicker)
3. Utilizarea a minim un formular de introducere a datelor/membru;
- [x] done
4. Transferul de parametri (obiecte proprii și primitive) între minim două activități;
- [x] done
5. Toate activitățile aplicației trebuie populate folosind controale vizuale corespunzătoare.
- [x] done

### Faza 2: (2.5 p)
1. Implementarea unui adaptor personalizat (cel puțin trei controale vizuale);
- [x] pending (Gherghe)
2. Implementarea și utilizarea unor operații asincrone; (1 p.)
- [x] done by Adela
3. Utilizarea claselor pentru accesul la resurse externe (din rețea); (0.5 p.)
- [x] done by Adela
4. Prelucrarea de fișiere JSON sau XML. Fișierele trebuie să conțină cel puțin 3 noduri dispuse pe niveluri diferite. Fiecare nod trebuie sa aibă cel puțin 3 atribute; (0.5 p.)
- [x] done by Adela

### Faza 3: (2.5 p)
1. Utilizarea fișierelor de preferințe; (0.5 p.)
- [x] pending (Adela)
2. Crearea unei baze de date SQLite cu minim două tabele cu legături intre ele (o tabelă/membru). Implementarea operațiilor de tip DDL; (0.5 p.)
- [x] done by Raluca
3. Implementarea operațiilor de tip DML. Pentru fiecare tabela sa se implementeze cel puțin o metoda de insert, update, delete si select. Toate metodele trebuie apelate; (0.5 p.)
- [x] mostly done, will have to be fully tested in Help and Contact (Raluca)
4. Definirea a minim două rapoarte pe datele stocate în baza de date. Prin raport, se înțelege afișarea pe ecranul dispozitivului mobil a informațiilor preluate din baza de date. Rapoartele sunt diferite ca si structura. (0.5 p.). Componentele vizuale în care se afișează cele doua rapoarte trebuie sa fie diferite de cele prezentate la faza 1 și 2
- [x] mostly done, will have to be fully tested in Help (Raluca)
5. Salvarea rapoartelor în fișiere normale. (txt, csv etc.) (0.5 p.)
- [ ] pending (Raluca)

### Final: (2.5 p)
1. Utilizarea bazelor de date la distanță (Firebase) (salvare/restaurare); Afișarea informațiilor din Firebase să se realizeze prin intermediul componentelor vizuale (se pot folosi activitățile deja prezentate în fazele anterioare) (0.75 p.)
- [ ] pending (Erik), done for question, questionnaire and response, will also be done for user
2. Utilizarea de elemente de grafică bidimensională, cu valori preluate din baza de date (locală sau la distanță); (0.5 p.)
- [x] pie chart for questionnaires, mostly done (Erik) - [references](https://stackoverflow.com/questions/20835628/how-to-draw-a-pie-chart-in-android)
3. Prelucrarea elementelor de tip imagine din diferite surse (imagini statice, preluate prin rețea, încărcate din galeria dispozitivului mobil, preluate din baze de date locală sau la distanță); Imaginile trebuie preluate din minim două surse. (0.5 p.)
- [ ] pending (Gherghe)
4. Stilizarea aplicației mobile (de exemplu, se creează o temă nouă în fișierul styles.xml sau
modificați fontul, culorile componentelor vizuale) (0.5 p)
- [ ] pending (Gherghe)
5. Implementarea unei funcționalități pe bază de Google Maps; (0.25 p.)
- [ ] pending (Adela)

### Misc tasks
1. Toate șirurile de caractere utilizate la nivelul interfeței trebuie preluate din resurse. Lipsa
acestora duce la o penalizare de 0.5 din fiecare faza.
- [ ] pending (Erik), mostly done
2. Game should be actally playable
- [ ] mostly done (Erik)

### Functionality tasks
- [x] Implement response list
- [x] Implement [Stats pie chart](https://stackoverflow.com/questions/20835628/how-to-draw-a-pie-chart-in-android)
- [ ] Implement dynamic User
- [x] Do not let user go back to a question and increase their score ([reference](https://stackoverflow.com/questions/4779954/disable-back-button-in-android))

### QA Checklist for 19.12.2018
- [x] scos stringuri hardcodate
- [x] fara crashuri
- [x] fara scame, injuraturi etc
- [ ] cu date de test in el

## Authors
- Frentescu Adela
- Ganea Raluca
- Gherghe Alexandru
- Kovacs Erik
