(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-taxregistration', {
            parent: 'entity',
            url: '/com-taxregistration?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_taxregistration.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-taxregistration/com-taxregistrations.html',
                    controller: 'Com_taxregistrationController',
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
                    $translatePartialLoader.addPart('com_taxregistration');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-taxregistration-detail', {
            parent: 'entity',
            url: '/com-taxregistration/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_taxregistration.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-taxregistration/com-taxregistration-detail.html',
                    controller: 'Com_taxregistrationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_taxregistration');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_taxregistration', function($stateParams, Com_taxregistration) {
                    return Com_taxregistration.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-taxregistration.new', {
            parent: 'com-taxregistration',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-taxregistration/com-taxregistration-dialog.html',
                    controller: 'Com_taxregistrationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                folio: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-taxregistration', null, { reload: true });
                }, function() {
                    $state.go('com-taxregistration');
                });
            }]
        })
        .state('com-taxregistration.edit', {
            parent: 'com-taxregistration',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-taxregistration/com-taxregistration-dialog.html',
                    controller: 'Com_taxregistrationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_taxregistration', function(Com_taxregistration) {
                            return Com_taxregistration.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-taxregistration', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-taxregistration.delete', {
            parent: 'com-taxregistration',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-taxregistration/com-taxregistration-delete-dialog.html',
                    controller: 'Com_taxregistrationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_taxregistration', function(Com_taxregistration) {
                            return Com_taxregistration.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-taxregistration', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
