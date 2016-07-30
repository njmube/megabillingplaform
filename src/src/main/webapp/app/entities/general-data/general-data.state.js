(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('general-data', {
            parent: 'entity',
            url: '/general-data?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.general_data.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/general-data/general-data.html',
                    controller: 'General_dataController',
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
                    $translatePartialLoader.addPart('general_data');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('general-data-detail', {
            parent: 'entity',
            url: '/general-data/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.general_data.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/general-data/general-data-detail.html',
                    controller: 'General_dataDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('general_data');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'General_data', function($stateParams, General_data) {
                    return General_data.get({id : $stateParams.id});
                }]
            }
        })
        .state('general-data.new', {
            parent: 'general-data',
            url: '/{id}/new',
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                    'content@': {
                        templateUrl: 'app/entities/general-data/general-data-new.html',
                        controller: 'General_dataNewController',
                        controllerAs: 'vm'
                    }
                },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('general_data');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'General_data', function($stateParams, General_data) {
                    return General_data.get({id : $stateParams.id});
                }]
            }
        })
        .state('general-data.edit', {
            parent: 'general-data',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/general-data/general-data-dialog.html',
                    controller: 'General_dataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['General_data', function(General_data) {
                            return General_data.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('general-data', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('general-data.delete', {
            parent: 'general-data',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/general-data/general-data-delete-dialog.html',
                    controller: 'General_dataDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['General_data', function(General_data) {
                            return General_data.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('general-data', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
