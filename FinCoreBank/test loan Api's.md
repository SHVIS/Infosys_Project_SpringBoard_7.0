Below is the **complete Loan Master API testing plan** with sample data insertion and different statuses.

Assumption:

```
Base URL:
http://localhost:9797
```

Your Loan Master table:

```
loan

loan_id
loan_amount
loan_tenure
total_tenure
interest_rate
emi_payable
total_interest_payable
total_cost
loan_status
```

---

# 1. Generate Loan ID

### API

```
GET /loan-id
```

### URL

```
http://localhost:9797/loan-id
```

### Expected Response

First record:

```json
"L1000001"
```

---

# 2. Create Active Loan (Admin)

Admin creates a loan scheme available for customers.

### API

```
POST /loan
```

### URL

```
http://localhost:9797/loan
```

### Body

```json
{
    "loanAmount":100000,
    "loanTenure":1,
    "interestRate":12
}
```

### System generates:

```
loanId = L1000001
totalTenure = 12
loanStatus = A
```

### Expected Database:

| Field         | Value    |
| ------------- | -------- |
| loan_id       | L1000001 |
| loan_amount   | 100000   |
| loan_tenure   | 1        |
| total_tenure  | 12       |
| interest_rate | 12       |
| loan_status   | A        |

---

# 3. Create Another Active Loan

### POST

```
/loan
```

### Body

```json
{
    "loanAmount":500000,
    "loanTenure":5,
    "interestRate":10
}
```

Generated:

```
loanId=L1000002
status=A
```

---

# 4. Create Inactive Loan

Normally admin creates it and later disables it.

For testing use update API.

### PUT

```
/loan
```

### Body

```json
{
    "loanId":"L1000002",
    "loanAmount":500000,
    "loanTenure":5,
    "interestRate":10,
    "loanStatus":"I"
}
```

Now:

```
L1000002 = Inactive
```

---

# 5. Create More Loans For Testing

## Active Loan

POST:

```json
{
    "loanAmount":200000,
    "loanTenure":3,
    "interestRate":15
}
```

Generated:

```
L1000003
A
```

---

## Inactive Loan

Update:

```json
{
    "loanId":"L1000003",
    "loanAmount":200000,
    "loanTenure":3,
    "interestRate":15,
    "loanStatus":"I"
}
```

---

# Final Database Example

After testing:

| loan_id  | amount | tenure | interest | status |
| -------- | ------ | ------ | -------- | ------ |
| L1000001 | 100000 | 1      | 12       | A      |
| L1000002 | 500000 | 5      | 10       | I      |
| L1000003 | 200000 | 3      | 15       | I      |

---

# 6. Get All Loans (Admin)

### GET

```
/loan
```

URL:

```
http://localhost:9797/loan
```

Expected:

```json
[
 {
  "loanId":"L1000001",
  "loanAmount":100000,
  "loanStatus":"A"
 },
 {
  "loanId":"L1000002",
  "loanAmount":500000,
  "loanStatus":"I"
 },
 {
  "loanId":"L1000003",
  "loanAmount":200000,
  "loanStatus":"I"
 }
]
```

---

# 7. Get Loan By ID

### GET

```
/loan/L1000001
```

Expected:

```json
{
 "loanId":"L1000001",
 "loanAmount":100000,
 "loanStatus":"A"
}
```

---

# 8. Customer Available Loan List

Customer should only see active loans.

### GET

```
/loan-list
```

Expected:

```json
[
 {
  "loanId":"L1000001",
  "loanAmount":100000,
  "loanStatus":"A"
 }
]
```

Inactive loans should not appear.

---

# 9. Filter By Status

## Active loans

### GET

```
/loan-status/A
```

Expected:

```json
[
 {
  "loanId":"L1000001",
  "loanStatus":"A"
 }
]
```

---

## Inactive loans

### GET

```
/loan-status/I
```

Expected:

```json
[
 {
  "loanId":"L1000002",
  "loanStatus":"I"
 },
 {
  "loanId":"L1000003",
  "loanStatus":"I"
 }
]
```

---

# 10. Update Active Loan

Admin changes interest rate.

### PUT

```
/loan
```

Body:

```json
{
 "loanId":"L1000001",
 "loanAmount":100000,
 "loanTenure":2,
 "interestRate":14,
 "loanStatus":"A"
}
```

System recalculates:

```
totalTenure
emiPayable
totalInterestPayable
totalCost
```

---

# 11. Minimum Amount Validation

### POST

```json
{
 "loanAmount":50000,
 "loanTenure":2,
 "interestRate":10
}
```

Expected:

```
RuntimeException:
Minimum loan amount should be 100000
```

---

# 12. Delete Loan

### DELETE

```
/loan/L1000003
```

Expected:

```
200 OK
```

Check:

```
GET /loan
```

Record should not exist.

---

## Final Expected Flow

```
Admin
 |
 |-- Create Loan Scheme
 |
 |-- Set Status A/I
 |
 |-- Update Scheme
 |
 |-- Delete Scheme
 |
Customer
 |
 |-- GET /loan-list
 |
 |-- See only A status loans
 |
 |-- Apply loan (CustomerLoan module next)
```

After this testing passes, your **Loan Master part is ready for CustomerLoan integration**.
