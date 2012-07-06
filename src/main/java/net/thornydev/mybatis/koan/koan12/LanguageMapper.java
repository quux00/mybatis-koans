package net.thornydev.mybatis.koan.koan12;

import net.thornydev.mybatis.koan.domain.Language;

public interface LanguageMapper {
  Language getLanguageById(int id);
}
