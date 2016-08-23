(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-tfd', {
            parent: 'entity',
            url: '/freecom-tfd?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_tfd.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-tfd/freecom-tfds.html',
                    controller: 'Freecom_tfdController',
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
                    $translatePartialLoader.addPart('freecom_tfd');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-tfd-detail', {
            parent: 'entity',
            url: '/freecom-tfd/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_tfd.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-tfd/freecom-tfd-detail.html',
                    controller: 'Freecom_tfdDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_tfd');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_tfd', function($stateParams, Freecom_tfd) {
                    return Freecom_tfd.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-tfd.new', {
            parent: 'freecom-tfd',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-tfd/freecom-tfd-dialog.html',
                    controller: 'Freecom_tfdDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                uuid: null,
                                stamp_date: null,
                                stamp_cfd: null,
                                sat_number_certificate: null,
                                stamp_sat: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-tfd', null, { reload: true });
                }, function() {
                    $state.go('freecom-tfd');
                });
            }]
        })
        .state('freecom-tfd.edit', {
            parent: 'freecom-tfd',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-tfd/freecom-tfd-dialog.html',
                    controller: 'Freecom_tfdDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_tfd', function(Freecom_tfd) {
                            return Freecom_tfd.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-tfd', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-tfd.delete', {
            parent: 'freecom-tfd',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-tfd/freecom-tfd-delete-dialog.html',
                    controller: 'Freecom_tfdDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_tfd', function(Freecom_tfd) {
                            return Freecom_tfd.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-tfd', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
