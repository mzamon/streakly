import unittest
from pathlib import Path


class ReadmeExistsTest(unittest.TestCase):
    def test_readme_exists(self):
        self.assertTrue((Path(__file__).parent / "README.md").is_file())


if __name__ == "__main__":
    unittest.main()
