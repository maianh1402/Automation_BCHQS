Feature: Login BCH

  @successfully
  Scenario: Verify form login
    Given User navigate url
    When User verify form
    Then User see "http://103.17.140.141:10201/login" page

  @failure
  Scenario Outline: Failure login
    Given User navigate url
    When User login "<account>" "<password>" and show error "<m_account>" "<m_password>"
    Then User see "http://103.17.140.141:10201/login" page
    Examples:
      | account      | password     | m_account                   | m_password          |
      | anh          | 12345@Au     |                             | Mật khẩu không đúng |
      | dhbewsebowie | 3e3hewbuewA@ | Tên tài khoản không tồn tại |                     |
      | anh          |              |                             | Mật khẩu bắt buộc   |
      |              | 12345@Au     | Tên tài khoản bắt buộc      |                     |
      |              |              | Tên tài khoản bắt buộc      | Mật khẩu bắt buộc   |

  @successfully
  Scenario: Successfully login
    Given User navigate url
    When User login "anh" "UunC1&tV" and show "Đăng nhập thành công"
    Then User see "http://103.17.140.141:10201/home" page