(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-destruction-certificate', {
            parent: 'entity',
            url: '/com-destruction-certificate?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_destruction_certificate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-destruction-certificate/com-destruction-certificates.html',
                    controller: 'Com_destruction_certificateController',
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
                    $translatePartialLoader.addPart('com_destruction_certificate');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-destruction-certificate-detail', {
            parent: 'entity',
            url: '/com-destruction-certificate/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_destruction_certificate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-destruction-certificate/com-destruction-certificate-detail.html',
                    controller: 'Com_destruction_certificateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_destruction_certificate');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_destruction_certificate', function($stateParams, Com_destruction_certificate) {
                    return Com_destruction_certificate.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-destruction-certificate.new', {
            parent: 'com-destruction-certificate',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-destruction-certificate/com-destruction-certificate-dialog.html',
                    controller: 'Com_destruction_certificateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                numfoldesveh: null,
                                brand: null,
                                class_dc: null,
                                year: null,
                                model: null,
                                niv: null,
                                no_serie: null,
                                number_plates: null,
                                number_engine: null,
                                numfoltarjcir: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-destruction-certificate', null, { reload: true });
                }, function() {
                    $state.go('com-destruction-certificate');
                });
            }]
        })
        .state('com-destruction-certificate.edit', {
            parent: 'com-destruction-certificate',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-destruction-certificate/com-destruction-certificate-dialog.html',
                    controller: 'Com_destruction_certificateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_destruction_certificate', function(Com_destruction_certificate) {
                            return Com_destruction_certificate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-destruction-certificate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-destruction-certificate.delete', {
            parent: 'com-destruction-certificate',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-destruction-certificate/com-destruction-certificate-delete-dialog.html',
                    controller: 'Com_destruction_certificateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_destruction_certificate', function(Com_destruction_certificate) {
                            return Com_destruction_certificate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-destruction-certificate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
