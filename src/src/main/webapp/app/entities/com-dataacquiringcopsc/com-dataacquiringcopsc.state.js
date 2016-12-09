(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-dataacquiringcopsc', {
            parent: 'entity',
            url: '/com-dataacquiringcopsc?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_dataacquiringcopsc.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-dataacquiringcopsc/com-dataacquiringcopscs.html',
                    controller: 'Com_dataacquiringcopscController',
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
                    $translatePartialLoader.addPart('com_dataacquiringcopsc');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-dataacquiringcopsc-detail', {
            parent: 'entity',
            url: '/com-dataacquiringcopsc/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_dataacquiringcopsc.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-dataacquiringcopsc/com-dataacquiringcopsc-detail.html',
                    controller: 'Com_dataacquiringcopscDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_dataacquiringcopsc');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_dataacquiringcopsc', function($stateParams, Com_dataacquiringcopsc) {
                    return Com_dataacquiringcopsc.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-dataacquiringcopsc.new', {
            parent: 'com-dataacquiringcopsc',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-dataacquiringcopsc/com-dataacquiringcopsc-dialog.html',
                    controller: 'Com_dataacquiringcopscDialogController',
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
                    $state.go('com-dataacquiringcopsc', null, { reload: true });
                }, function() {
                    $state.go('com-dataacquiringcopsc');
                });
            }]
        })
        .state('com-dataacquiringcopsc.edit', {
            parent: 'com-dataacquiringcopsc',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-dataacquiringcopsc/com-dataacquiringcopsc-dialog.html',
                    controller: 'Com_dataacquiringcopscDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_dataacquiringcopsc', function(Com_dataacquiringcopsc) {
                            return Com_dataacquiringcopsc.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-dataacquiringcopsc', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-dataacquiringcopsc.delete', {
            parent: 'com-dataacquiringcopsc',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-dataacquiringcopsc/com-dataacquiringcopsc-delete-dialog.html',
                    controller: 'Com_dataacquiringcopscDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_dataacquiringcopsc', function(Com_dataacquiringcopsc) {
                            return Com_dataacquiringcopsc.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-dataacquiringcopsc', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
