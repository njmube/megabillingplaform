(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-addressee', {
            parent: 'entity',
            url: '/freecom-addressee?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_addressee.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-addressee/freecom-addressees.html',
                    controller: 'Freecom_addresseeController',
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
                    $translatePartialLoader.addPart('freecom_addressee');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-addressee-detail', {
            parent: 'entity',
            url: '/freecom-addressee/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_addressee.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-addressee/freecom-addressee-detail.html',
                    controller: 'Freecom_addresseeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_addressee');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_addressee', function($stateParams, Freecom_addressee) {
                    return Freecom_addressee.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('freecom-addressee.new', {
            parent: 'freecom-addressee',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-addressee/freecom-addressee-dialog.html',
                    controller: 'Freecom_addresseeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                street: null,
                                no_ext: null,
                                no_int: null,
                                locate: null,
                                reference: null,
                                numregidtrib: null,
                                rfc: null,
                                curp: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-addressee', null, { reload: true });
                }, function() {
                    $state.go('freecom-addressee');
                });
            }]
        })
        .state('freecom-addressee.edit', {
            parent: 'freecom-addressee',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-addressee/freecom-addressee-dialog.html',
                    controller: 'Freecom_addresseeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_addressee', function(Freecom_addressee) {
                            return Freecom_addressee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-addressee', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-addressee.delete', {
            parent: 'freecom-addressee',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-addressee/freecom-addressee-delete-dialog.html',
                    controller: 'Freecom_addresseeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_addressee', function(Freecom_addressee) {
                            return Freecom_addressee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-addressee', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
