import unittest
from pathlib import Path


class ReadmeExistsTest(unittest.TestCase):
    def test_readme_exists(self):
        readme_path = Path(__file__).parent / "ReadMe.md"
        self.assertTrue(readme_path.is_file())


if __name__ == "__main__":
    unittest.main()
