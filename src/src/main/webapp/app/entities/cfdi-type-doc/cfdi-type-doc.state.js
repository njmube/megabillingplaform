(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cfdi-type-doc', {
            parent: 'entity',
            url: '/cfdi-type-doc?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.cfdi_type_doc.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cfdi-type-doc/cfdi-type-docs.html',
                    controller: 'Cfdi_type_docController',
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
                    $translatePartialLoader.addPart('cfdi_type_doc');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cfdi-type-doc-detail', {
            parent: 'entity',
            url: '/cfdi-type-doc/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.cfdi_type_doc.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cfdi-type-doc/cfdi-type-doc-detail.html',
                    controller: 'Cfdi_type_docDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cfdi_type_doc');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cfdi_type_doc', function($stateParams, Cfdi_type_doc) {
                    return Cfdi_type_doc.get({id : $stateParams.id});
                }]
            }
        })
        .state('cfdi-type-doc.new', {
            parent: 'cfdi-type-doc',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfdi-type-doc/cfdi-type-doc-dialog.html',
                    controller: 'Cfdi_type_docDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cfdi-type-doc', null, { reload: true });
                }, function() {
                    $state.go('cfdi-type-doc');
                });
            }]
        })
        .state('cfdi-type-doc.edit', {
            parent: 'cfdi-type-doc',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfdi-type-doc/cfdi-type-doc-dialog.html',
                    controller: 'Cfdi_type_docDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cfdi_type_doc', function(Cfdi_type_doc) {
                            return Cfdi_type_doc.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('cfdi-type-doc', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cfdi-type-doc.delete', {
            parent: 'cfdi-type-doc',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfdi-type-doc/cfdi-type-doc-delete-dialog.html',
                    controller: 'Cfdi_type_docDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cfdi_type_doc', function(Cfdi_type_doc) {
                            return Cfdi_type_doc.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('cfdi-type-doc', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
