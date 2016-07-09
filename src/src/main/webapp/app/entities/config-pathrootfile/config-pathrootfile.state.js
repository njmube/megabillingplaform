(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('config-pathrootfile', {
            parent: 'entity',
            url: '/config-pathrootfile?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.config_pathrootfile.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/config-pathrootfile/config-pathrootfiles.html',
                    controller: 'Config_pathrootfileController',
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
                    $translatePartialLoader.addPart('config_pathrootfile');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('config-pathrootfile-detail', {
            parent: 'entity',
            url: '/config-pathrootfile/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.config_pathrootfile.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/config-pathrootfile/config-pathrootfile-detail.html',
                    controller: 'Config_pathrootfileDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('config_pathrootfile');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Config_pathrootfile', function($stateParams, Config_pathrootfile) {
                    return Config_pathrootfile.get({id : $stateParams.id});
                }]
            }
        })
        .state('config-pathrootfile.new', {
            parent: 'config-pathrootfile',
            url: '/{id}/new',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.config_pathrootfile.detail.title'
            },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/config-pathrootfile/config-pathrootfile-new.html',
                        controller: 'Config_pathrootfileNewController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('config_pathrootfile');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Config_pathrootfile', function($stateParams, Config_pathrootfile) {
                        return Config_pathrootfile.get({id : $stateParams.id});
                    }]
                }

        })
        .state('config-pathrootfile.edit', {
            parent: 'config-pathrootfile',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/config-pathrootfile/config-pathrootfile-dialog.html',
                    controller: 'Config_pathrootfileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Config_pathrootfile', function(Config_pathrootfile) {
                            return Config_pathrootfile.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('config-pathrootfile', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('config-pathrootfile.delete', {
            parent: 'config-pathrootfile',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/config-pathrootfile/config-pathrootfile-delete-dialog.html',
                    controller: 'Config_pathrootfileDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Config_pathrootfile', function(Config_pathrootfile) {
                            return Config_pathrootfile.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('config-pathrootfile', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
