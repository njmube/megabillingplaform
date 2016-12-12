(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-dataacquiringcopsc', {
            parent: 'entity',
            url: '/freecom-dataacquiringcopsc?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_dataacquiringcopsc.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-dataacquiringcopsc/freecom-dataacquiringcopscs.html',
                    controller: 'Freecom_dataacquiringcopscController',
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
                    $translatePartialLoader.addPart('freecom_dataacquiringcopsc');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-dataacquiringcopsc-detail', {
            parent: 'entity',
            url: '/freecom-dataacquiringcopsc/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_dataacquiringcopsc.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-dataacquiringcopsc/freecom-dataacquiringcopsc-detail.html',
                    controller: 'Freecom_dataacquiringcopscDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_dataacquiringcopsc');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_dataacquiringcopsc', function($stateParams, Freecom_dataacquiringcopsc) {
                    return Freecom_dataacquiringcopsc.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-dataacquiringcopsc.new', {
            parent: 'freecom-dataacquiringcopsc',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-dataacquiringcopsc/freecom-dataacquiringcopsc-dialog.html',
                    controller: 'Freecom_dataacquiringcopscDialogController',
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
                    $state.go('freecom-dataacquiringcopsc', null, { reload: true });
                }, function() {
                    $state.go('freecom-dataacquiringcopsc');
                });
            }]
        })
        .state('freecom-dataacquiringcopsc.edit', {
            parent: 'freecom-dataacquiringcopsc',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-dataacquiringcopsc/freecom-dataacquiringcopsc-dialog.html',
                    controller: 'Freecom_dataacquiringcopscDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_dataacquiringcopsc', function(Freecom_dataacquiringcopsc) {
                            return Freecom_dataacquiringcopsc.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-dataacquiringcopsc', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-dataacquiringcopsc.delete', {
            parent: 'freecom-dataacquiringcopsc',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-dataacquiringcopsc/freecom-dataacquiringcopsc-delete-dialog.html',
                    controller: 'Freecom_dataacquiringcopscDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_dataacquiringcopsc', function(Freecom_dataacquiringcopsc) {
                            return Freecom_dataacquiringcopsc.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-dataacquiringcopsc', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
