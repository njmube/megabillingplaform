(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-taxregistration', {
            parent: 'entity',
            url: '/freecom-taxregistration?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_taxregistration.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-taxregistration/freecom-taxregistrations.html',
                    controller: 'Freecom_taxregistrationController',
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
                    $translatePartialLoader.addPart('freecom_taxregistration');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-taxregistration-detail', {
            parent: 'entity',
            url: '/freecom-taxregistration/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_taxregistration.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-taxregistration/freecom-taxregistration-detail.html',
                    controller: 'Freecom_taxregistrationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_taxregistration');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_taxregistration', function($stateParams, Freecom_taxregistration) {
                    return Freecom_taxregistration.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-taxregistration.new', {
            parent: 'freecom-taxregistration',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-taxregistration/freecom-taxregistration-dialog.html',
                    controller: 'Freecom_taxregistrationDialogController',
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
                    $state.go('freecom-taxregistration', null, { reload: true });
                }, function() {
                    $state.go('freecom-taxregistration');
                });
            }]
        })
        .state('freecom-taxregistration.edit', {
            parent: 'freecom-taxregistration',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-taxregistration/freecom-taxregistration-dialog.html',
                    controller: 'Freecom_taxregistrationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_taxregistration', function(Freecom_taxregistration) {
                            return Freecom_taxregistration.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-taxregistration', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-taxregistration.delete', {
            parent: 'freecom-taxregistration',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-taxregistration/freecom-taxregistration-delete-dialog.html',
                    controller: 'Freecom_taxregistrationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_taxregistration', function(Freecom_taxregistration) {
                            return Freecom_taxregistration.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-taxregistration', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
