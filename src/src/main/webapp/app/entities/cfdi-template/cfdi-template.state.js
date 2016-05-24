(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cfdi-template', {
            parent: 'entity',
            url: '/cfdi-template?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.cfdi_template.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cfdi-template/cfdi-templates.html',
                    controller: 'Cfdi_templateController',
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
                    $translatePartialLoader.addPart('cfdi_template');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cfdi-template-detail', {
            parent: 'entity',
            url: '/cfdi-template/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.cfdi_template.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cfdi-template/cfdi-template-detail.html',
                    controller: 'Cfdi_templateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cfdi_template');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cfdi_template', function($stateParams, Cfdi_template) {
                    return Cfdi_template.get({id : $stateParams.id});
                }]
            }
        })
        .state('cfdi-template.new', {
            parent: 'cfdi-template',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfdi-template/cfdi-template-dialog.html',
                    controller: 'Cfdi_templateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                template: null,
                                file: null,
                                fileContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cfdi-template', null, { reload: true });
                }, function() {
                    $state.go('cfdi-template');
                });
            }]
        })
        .state('cfdi-template.edit', {
            parent: 'cfdi-template',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfdi-template/cfdi-template-dialog.html',
                    controller: 'Cfdi_templateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cfdi_template', function(Cfdi_template) {
                            return Cfdi_template.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('cfdi-template', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cfdi-template.delete', {
            parent: 'cfdi-template',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfdi-template/cfdi-template-delete-dialog.html',
                    controller: 'Cfdi_templateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cfdi_template', function(Cfdi_template) {
                            return Cfdi_template.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('cfdi-template', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
