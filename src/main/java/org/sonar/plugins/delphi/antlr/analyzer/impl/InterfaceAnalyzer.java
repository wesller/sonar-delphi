/*
 * Sonar Delphi Plugin
 * Copyright (C) 2011 Sabre Airline Solutions
 * Author(s):
 * Przemyslaw Kociolek (przemyslaw.kociolek@sabre.com)
 * Michal Wojcik (michal.wojcik@sabre.com)
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.delphi.antlr.analyzer.impl;

import org.antlr.runtime.tree.Tree;
import org.sonar.plugins.delphi.antlr.DelphiLexer;
import org.sonar.plugins.delphi.antlr.DelphiParser;
import org.sonar.plugins.delphi.antlr.analyzer.CodeAnalysisResults;
import org.sonar.plugins.delphi.antlr.analyzer.CodeAnalyzer;
import org.sonar.plugins.delphi.antlr.analyzer.CodeTree;
import org.sonar.plugins.delphi.antlr.analyzer.LexerMetrics;

/**
 * Set the section of file currently being parsed (interface / implementation sections)
 */
public class InterfaceAnalyzer extends CodeAnalyzer {

  @Override
  public void doAnalyze(CodeTree codeTree, CodeAnalysisResults results) {
    LexerMetrics lexerMetrics = LexerMetrics.getLexerMetricsForType(codeTree.getCurrentCodeNode().getNode().getType());
    if (lexerMetrics == LexerMetrics.IMPLEMENTATION) {
      results.setActiveClass(null);
      results.setParseVisibility(LexerMetrics.PRIVATE);
    }

    results.setParseStatus(lexerMetrics);
  }

  @Override
  public boolean canAnalyze(CodeTree codeTree) {
    Tree currentNode = codeTree.getCurrentCodeNode().getNode();
    int type = currentNode.getType();
    return (type == DelphiLexer.INTERFACE || type == DelphiParser.IMPLEMENTATION);
  }

}
