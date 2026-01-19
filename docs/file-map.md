# 4.3.3 File Map

This document defines the contract between backend routes and frontend templates.
Each backend route maps directly to a single HTML template.

---

## Backend Route to Frontend Template Mapping

### Authentication Pages

| HTTP Method | Route        | Template       | Description |
|------------|--------------|----------------|-------------|
| GET        | /login       | login.html     | Displays the user login form |
| GET        | /register    | register.html  | Displays the user registration form |

---

### Admin User Management Pages

| HTTP Method | Route              | Template               | Description |
|------------|--------------------|------------------------|-------------|
| GET        | /admin/users       | userAdmin.html         | Displays a list of all users |
| GET        | /admin/users/edit  | editUser.html          | Displays a form to edit a selected user |
| GET        | /admin/users/delete| confirmDeleteUser.html | Displays a confirmation page before deleting a user |

---