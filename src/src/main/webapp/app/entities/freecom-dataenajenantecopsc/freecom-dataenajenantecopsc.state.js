(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-dataenajenantecopsc', {
            parent: 'entity',
            url: '/freecom-dataenajenantecopsc?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_dataenajenantecopsc.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-dataenajenantecopsc/freecom-dataenajenantecopscs.html',
                    controller: 'Freecom_dataenajenantecopscController',
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
                    $translatePartialLoader.addPart('freecom_dataenajenantecopsc');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-dataenajenantecopsc-detail', {
            parent: 'entity',
            url: '/freecom-dataenajenantecopsc/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_dataenajenantecopsc.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-dataenajenantecopsc/freecom-dataenajenantecopsc-detail.html',
                    controller: 'Freecom_dataenajenantecopscDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_dataenajenantecopsc');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_dataenajenantecopsc', function($stateParams, Freecom_dataenajenantecopsc) {
                    return Freecom_dataenajenantecopsc.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-dataenajenantecopsc.new', {
            parent: 'freecom-dataenajenantecopsc',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-dataenajenantecopsc/freecom-dataenajenantecopsc-dialog.html',
                    controller: 'Freecom_dataenajenantecopscDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                last_name: null,
                                mother_last_name: null,
                                rfc: null,
                                curp: null,
                                percentage: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-dataenajenantecopsc', null, { reload: true });
                }, function() {
                    $state.go('freecom-dataenajenantecopsc');
                });
            }]
        })
        .state('freecom-dataenajenantecopsc.edit', {
            parent: 'freecom-dataenajenantecopsc',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-dataenajenantecopsc/freecom-dataenajenantecopsc-dialog.html',
                    controller: 'Freecom_dataenajenantecopscDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_dataenajenantecopsc', function(Freecom_dataenajenantecopsc) {
                            return Freecom_dataenajenantecopsc.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-dataenajenantecopsc', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-dataenajenantecopsc.delete', {
            parent: 'freecom-dataenajenantecopsc',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-dataenajenantecopsc/freecom-dataenajenantecopsc-delete-dialog.html',
                    controller: 'Freecom_dataenajenantecopscDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_dataenajenantecopsc', function(Freecom_dataenajenantecopsc) {
                            return Freecom_dataenajenantecopsc.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-dataenajenantecopsc', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
