(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-notary-data', {
            parent: 'entity',
            url: '/freecom-notary-data?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_notary_data.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-notary-data/freecom-notary-data.html',
                    controller: 'Freecom_notary_dataController',
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
                    $translatePartialLoader.addPart('freecom_notary_data');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-notary-data-detail', {
            parent: 'entity',
            url: '/freecom-notary-data/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_notary_data.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-notary-data/freecom-notary-data-detail.html',
                    controller: 'Freecom_notary_dataDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_notary_data');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_notary_data', function($stateParams, Freecom_notary_data) {
                    return Freecom_notary_data.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-notary-data.new', {
            parent: 'freecom-notary-data',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-notary-data/freecom-notary-data-dialog.html',
                    controller: 'Freecom_notary_dataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                curp: null,
                                notarynumber: null,
                                ascription: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-notary-data', null, { reload: true });
                }, function() {
                    $state.go('freecom-notary-data');
                });
            }]
        })
        .state('freecom-notary-data.edit', {
            parent: 'freecom-notary-data',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-notary-data/freecom-notary-data-dialog.html',
                    controller: 'Freecom_notary_dataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_notary_data', function(Freecom_notary_data) {
                            return Freecom_notary_data.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-notary-data', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-notary-data.delete', {
            parent: 'freecom-notary-data',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-notary-data/freecom-notary-data-delete-dialog.html',
                    controller: 'Freecom_notary_dataDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_notary_data', function(Freecom_notary_data) {
                            return Freecom_notary_data.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-notary-data', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
