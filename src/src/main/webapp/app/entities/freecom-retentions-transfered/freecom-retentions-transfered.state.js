(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-retentions-transfered', {
            parent: 'entity',
            url: '/freecom-retentions-transfered?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_retentions_transfered.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-retentions-transfered/freecom-retentions-transfereds.html',
                    controller: 'Freecom_retentions_transferedController',
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
                    $translatePartialLoader.addPart('freecom_retentions_transfered');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-retentions-transfered-detail', {
            parent: 'entity',
            url: '/freecom-retentions-transfered/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_retentions_transfered.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-retentions-transfered/freecom-retentions-transfered-detail.html',
                    controller: 'Freecom_retentions_transferedDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_retentions_transfered');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_retentions_transfered', function($stateParams, Freecom_retentions_transfered) {
                    return Freecom_retentions_transfered.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-retentions-transfered.new', {
            parent: 'freecom-retentions-transfered',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-retentions-transfered/freecom-retentions-transfered-dialog.html',
                    controller: 'Freecom_retentions_transferedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                implocretentions: null,
                                retentionrate: null,
                                amountretentions: null,
                                imploctransfered: null,
                                transferedrate: null,
                                amounttransfered: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-retentions-transfered', null, { reload: true });
                }, function() {
                    $state.go('freecom-retentions-transfered');
                });
            }]
        })
        .state('freecom-retentions-transfered.edit', {
            parent: 'freecom-retentions-transfered',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-retentions-transfered/freecom-retentions-transfered-dialog.html',
                    controller: 'Freecom_retentions_transferedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_retentions_transfered', function(Freecom_retentions_transfered) {
                            return Freecom_retentions_transfered.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-retentions-transfered', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-retentions-transfered.delete', {
            parent: 'freecom-retentions-transfered',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-retentions-transfered/freecom-retentions-transfered-delete-dialog.html',
                    controller: 'Freecom_retentions_transferedDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_retentions_transfered', function(Freecom_retentions_transfered) {
                            return Freecom_retentions_transfered.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-retentions-transfered', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
