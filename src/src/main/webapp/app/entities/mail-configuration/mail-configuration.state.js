(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('mail-configuration', {
            parent: 'entity',
            url: '/mail-configuration?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.mail_configuration.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mail-configuration/mail-configurations.html',
                    controller: 'Mail_configurationController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('mail_configuration');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('mail-configuration-detail', {
            parent: 'entity',
            url: '/mail-configuration/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.mail_configuration.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/mail-configuration/mail-configuration-detail.html',
                    controller: 'Mail_configurationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('mail_configuration');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Mail_configuration', function($stateParams, Mail_configuration) {
                    return Mail_configuration.get({id : $stateParams.id});
                }]
            }
        })
        .state('mail-configuration.new', {
            parent: 'mail-configuration',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mail-configuration/mail-configuration-dialog.html',
                    controller: 'Mail_configurationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                host: null,
                                port: null,
                                user_mail: null,
                                password_mail: null,
                                protocol: null,
                                tls: null,
                                smtp_auth: null,
                                starttls_enable: null,
                                ssl_trust: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('mail-configuration', null, { reload: true });
                }, function() {
                    $state.go('mail-configuration');
                });
            }]
        })
        .state('mail-configuration.edit', {
            parent: 'mail-configuration',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mail-configuration/mail-configuration-dialog.html',
                    controller: 'Mail_configurationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Mail_configuration', function(Mail_configuration) {
                            return Mail_configuration.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('mail-configuration', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('mail-configuration.delete', {
            parent: 'mail-configuration',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/mail-configuration/mail-configuration-delete-dialog.html',
                    controller: 'Mail_configurationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Mail_configuration', function(Mail_configuration) {
                            return Mail_configuration.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('mail-configuration', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
