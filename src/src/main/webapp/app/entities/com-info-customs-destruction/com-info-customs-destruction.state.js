(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-info-customs-destruction', {
            parent: 'entity',
            url: '/com-info-customs-destruction?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_info_customs_destruction.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-info-customs-destruction/com-info-customs-destructions.html',
                    controller: 'Com_info_customs_destructionController',
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
                    $translatePartialLoader.addPart('com_info_customs_destruction');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-info-customs-destruction-detail', {
            parent: 'entity',
            url: '/com-info-customs-destruction/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_info_customs_destruction.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-info-customs-destruction/com-info-customs-destruction-detail.html',
                    controller: 'Com_info_customs_destructionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_info_customs_destruction');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_info_customs_destruction', function($stateParams, Com_info_customs_destruction) {
                    return Com_info_customs_destruction.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-info-customs-destruction.new', {
            parent: 'com-info-customs-destruction',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-info-customs-destruction/com-info-customs-destruction-dialog.html',
                    controller: 'Com_info_customs_destructionDialogController',
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
                    $state.go('com-info-customs-destruction', null, { reload: true });
                }, function() {
                    $state.go('com-info-customs-destruction');
                });
            }]
        })
        .state('com-info-customs-destruction.edit', {
            parent: 'com-info-customs-destruction',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-info-customs-destruction/com-info-customs-destruction-dialog.html',
                    controller: 'Com_info_customs_destructionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_info_customs_destruction', function(Com_info_customs_destruction) {
                            return Com_info_customs_destruction.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-info-customs-destruction', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-info-customs-destruction.delete', {
            parent: 'com-info-customs-destruction',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-info-customs-destruction/com-info-customs-destruction-delete-dialog.html',
                    controller: 'Com_info_customs_destructionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_info_customs_destruction', function(Com_info_customs_destruction) {
                            return Com_info_customs_destruction.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-info-customs-destruction', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
