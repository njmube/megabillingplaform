(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-info-customs-destruction', {
            parent: 'entity',
            url: '/freecom-info-customs-destruction?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_info_customs_destruction.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-info-customs-destruction/freecom-info-customs-destructions.html',
                    controller: 'Freecom_info_customs_destructionController',
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
                    $translatePartialLoader.addPart('freecom_info_customs_destruction');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-info-customs-destruction-detail', {
            parent: 'entity',
            url: '/freecom-info-customs-destruction/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_info_customs_destruction.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-info-customs-destruction/freecom-info-customs-destruction-detail.html',
                    controller: 'Freecom_info_customs_destructionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_info_customs_destruction');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_info_customs_destruction', function($stateParams, Freecom_info_customs_destruction) {
                    return Freecom_info_customs_destruction.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-info-customs-destruction.new', {
            parent: 'freecom-info-customs-destruction',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-info-customs-destruction/freecom-info-customs-destruction-dialog.html',
                    controller: 'Freecom_info_customs_destructionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numpedimp: null,
                                date_expedition: null,
                                customs: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-info-customs-destruction', null, { reload: true });
                }, function() {
                    $state.go('freecom-info-customs-destruction');
                });
            }]
        })
        .state('freecom-info-customs-destruction.edit', {
            parent: 'freecom-info-customs-destruction',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-info-customs-destruction/freecom-info-customs-destruction-dialog.html',
                    controller: 'Freecom_info_customs_destructionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_info_customs_destruction', function(Freecom_info_customs_destruction) {
                            return Freecom_info_customs_destruction.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-info-customs-destruction', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-info-customs-destruction.delete', {
            parent: 'freecom-info-customs-destruction',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-info-customs-destruction/freecom-info-customs-destruction-delete-dialog.html',
                    controller: 'Freecom_info_customs_destructionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_info_customs_destruction', function(Freecom_info_customs_destruction) {
                            return Freecom_info_customs_destruction.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-info-customs-destruction', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
