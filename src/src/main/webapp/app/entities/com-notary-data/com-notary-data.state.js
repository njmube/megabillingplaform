(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-notary-data', {
            parent: 'entity',
            url: '/com-notary-data?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_notary_data.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-notary-data/com-notary-data.html',
                    controller: 'Com_notary_dataController',
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
                    $translatePartialLoader.addPart('com_notary_data');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-notary-data-detail', {
            parent: 'entity',
            url: '/com-notary-data/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_notary_data.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-notary-data/com-notary-data-detail.html',
                    controller: 'Com_notary_dataDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_notary_data');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_notary_data', function($stateParams, Com_notary_data) {
                    return Com_notary_data.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-notary-data.new', {
            parent: 'com-notary-data',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-notary-data/com-notary-data-dialog.html',
                    controller: 'Com_notary_dataDialogController',
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
                    $state.go('com-notary-data', null, { reload: true });
                }, function() {
                    $state.go('com-notary-data');
                });
            }]
        })
        .state('com-notary-data.edit', {
            parent: 'com-notary-data',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-notary-data/com-notary-data-dialog.html',
                    controller: 'Com_notary_dataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_notary_data', function(Com_notary_data) {
                            return Com_notary_data.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-notary-data', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-notary-data.delete', {
            parent: 'com-notary-data',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-notary-data/com-notary-data-delete-dialog.html',
                    controller: 'Com_notary_dataDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_notary_data', function(Com_notary_data) {
                            return Com_notary_data.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-notary-data', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
