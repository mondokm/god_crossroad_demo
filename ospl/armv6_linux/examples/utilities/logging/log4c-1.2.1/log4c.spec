# $Id$ 

%define RELEASE 1
%define rel     %{?CUSTOM_RELEASE} %{!?CUSTOM_RELEASE:%RELEASE}
%define _unpackaged_files_terminate_build 0

Name: 		log4c
Version: 	1.2.1
Release: 	%rel

Summary: 	Log for C
License:	LGPL
Group:		Development/Libraries
Vendor:		Cedric Le Goater <legoater@free.fr>
Packager:	Cedric Le Goater <legoater@free.fr>
Url:		http://%name.sourceforge.net/
Source:		http://prdownloads.sourceforge.net/%name/%name-%version.tar.gz
BuildRoot:	%_topdir/%name-%version-root
BuildRequires: 	doxygen
Requires: 	/sbin/ldconfig

%description
%name is a Logging FrameWork for C, as Log4j or Log4Cpp.

%package devel
Summary: development tools for %name
Group: Development/Libraries
Requires: %name = %version

%package doc
Summary: documentation for %name
Group: Development/Libraries
Requires: %name = %version

%description devel
The %name-devel package contains the static libraries and header files
needed for development with %name.

%description doc
The %name-doc package contains the %name documentation

%prep
%setup -q

%build
%configure --enable-doc
make

%install
rm -rf %{buildroot}
%makeinstall

%clean
rm -rf %{buildroot}

%post -p /sbin/ldconfig

%postun -p /sbin/ldconfig

%files
%defattr(-,root,root)
%doc AUTHORS COPYING ChangeLog NEWS README
%{_sysconfdir}/*
%{_libdir}/*.so.*

%files devel
%defattr(-,root,root)
%{_bindir}/*
%{_includedir}/*
%{_libdir}/*.a
%{_libdir}/*.la
%{_libdir}/*.so
%{_datadir}/aclocal/*
%{_mandir}/*

%files doc
%defattr(-,root,root)
%doc doc/%{name}.pdf doc/html

%changelog
* Mon Feb 21 2002 Cedric Le Goater
- Initial RPM release.

# Local Variables:
# mode:rpm-spec
# End:
