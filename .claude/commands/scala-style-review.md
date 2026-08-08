Review Scala code for style consistency and idiomatic Scala 3 usage. Primary focus is style, not logic/architecture.

## Instructions

**Step 1 — Determine scope:**

- If $ARGUMENTS is provided, treat it as a path, file list, or git ref range to review.
- Otherwise, review the current uncommitted changes: run `git diff HEAD -- '*.scala'` and `git status --short -- '*.scala'`.
- If there is no diff, fall back to reviewing all tracked `*.scala` files under `src/`.

**Step 2 — Check formatting compliance:**

Run `scalafmt --test` (or `sbt scalafmtCheck` if scalafmt isn't on PATH) against the reviewed files if a formatter is available. Read [.scalafmt.conf](.scalafmt.conf) first to know the configured dialect (`scala3`) and version. Report any files that would be reformatted, but don't hand-simulate a full reformat — just flag it and suggest running `scalafmt`.

**Step 3 — Manual style review:**

Read each in-scope file in full (not just the diff hunks) so style issues are judged in context. For each finding include: file path, line number, the issue, and a concrete fix.

### Style checklist

1. **Indentation & braces** — Consistent use of either brace-delimited or Scala 3 significant-indentation syntax within a file; don't mix. Flag inconsistent indent width (this codebase uses 2 spaces per [.scalafmt.conf](.scalafmt.conf); flag 4-space blocks like tabs-vs-spaces drift).
2. **Naming conventions** — `UpperCamelCase` for classes/traits/objects, `lowerCamelCase` for methods/values/vars, `UPPER_SNAKE_CASE` only for true constants placed in companion objects. No Hungarian notation, no underscores in identifiers.
3. **Immutability by default** — Flag `var` where `val` would work, mutable collections where immutable ones suffice, and reassignment patterns that could be expressions instead.
4. **Scala 3 idioms** — Prefer `given`/`using` over implicit defs where applicable, enums (`enum ... derives`) over sealed trait + case object boilerplate, `apply`/extension methods over utility objects, and new control-syntax (`if ... then`, `for ... do`) consistently if the file has already adopted it. Don't force a rewrite of untouched code just to chase idiom purity.
5. **Expression-oriented style** — Prefer expressions over statements: avoid `var` + mutation loops when `map`/`filter`/`fold`/pattern matching reads better; avoid returning via side-effecting assignment when a function can just return a value.
6. **Pattern matching & exhaustiveness** — Flag `asInstanceOf`, unchecked casts, or `match` blocks missing an obvious case (especially over sealed hierarchies/enums) instead of relying on `case _ =>` catch-alls that swallow bugs.
7. **Error handling style** — Flag bare `catch { case e: Exception => ... }` (swallows everything, including fatal errors); prefer catching specific exception types or returning `Try`/`Either`/`Option`. Flag `null` usage where `Option` is idiomatic — but note when it's forced by interop with a Java API (e.g. `getResource`, AWT/Swing).
8. **Imports** — No wildcard imports (`import scala.swing._`) except where conventional for the ecosystem (e.g. Swing/AWT — note but don't hard-flag); no unused imports; imports grouped/ordered consistently.
9. **Braces-as-blocks for single expressions** — Flag unnecessary `{ }` around single-expression bodies, and unnecessary parens around no-arg calls.
10. **Line length & wrapping** — Match the wrapping style already implied by `.scalafmt.conf` (default 80 col unless configured otherwise); flag lines that a formatter would clearly need to wrap.
11. **Self-type / this-aliasing** — Flag `frame =>` / `self =>` style self-references used without being referenced in the body, or used where a plain reference to the outer instance would do.
12. **Doc comments** — Flag missing or unconventional Scaladoc (`/** ... */`) only on public API surfaces that clearly need it (non-obvious behavior); don't demand comments on self-explanatory code, consistent with this repo's minimal-comment convention.

## Output Format

Start with a one-line **summary**: file count reviewed, formatter status (clean/needs `scalafmt`), and overall style consistency impression.

Then group findings by file:

---
### `path/to/File.scala`
- **[CHECKLIST ITEM]** `SEVERITY` — line N: explanation. Fix: ...
---

Severity is one of: **MUST-FIX** (breaks formatting/build or clearly wrong), **SUGGESTION** (idiomatic improvement), **NITPICK** (minor/subjective).

End with a **verdict**: STYLE CLEAN / MINOR ISSUES / NEEDS CLEANUP, plus the exact command to run (e.g. `scalafmt` or `sbt scalafmtAll`) if formatting fixes are needed.
