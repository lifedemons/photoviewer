Feature: Browse Photos List

  Background:
    Given User opens Photos List

  @ScenarioId("FUNCTIONAL.LIST.001") @functional-scenarios
  Scenario: User can browse list
    Then User sees photo with title "a at voluptatem"

  @ScenarioId("FUNCTIONAL.LIST.002") @functional-scenarios
  Scenario: User can search for certain photo by title
    When User searches for photo with title "a ea culpa eius"
    Then User sees photo with title "a ea culpa eius"