(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('free-digital-certificate', {
            parent: 'entity',
            url: '/free-digital-certificate?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.free_digital_certificate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-digital-certificate/free-digital-certificates.html',
                    controller: 'Free_digital_certificateController',
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
                    $translatePartialLoader.addPart('free_digital_certificate');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('free-digital-certificate-detail', {
            parent: 'entity',
            url: '/free-digital-certificate/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.free_digital_certificate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-digital-certificate/free-digital-certificate-detail.html',
                    controller: 'Free_digital_certificateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('free_digital_certificate');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Free_digital_certificate', function($stateParams, Free_digital_certificate) {
                    return Free_digital_certificate.get({id : $stateParams.id});
                }]
            }
        })
        .state('free-digital-certificate.new', {
            parent: 'free-digital-certificate',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-digital-certificate/free-digital-certificate-dialog.html',
                    controller: 'Free_digital_certificateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                adrees: null,
                                adreesContentType: null,
                                private_key: null,
                                private_keyContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('free-digital-certificate', null, { reload: true });
                }, function() {
                    $state.go('free-digital-certificate');
                });
            }]
        })
        .state('free-digital-certificate.edit', {
            parent: 'free-digital-certificate',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-digital-certificate/free-digital-certificate-dialog.html',
                    controller: 'Free_digital_certificateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Free_digital_certificate', function(Free_digital_certificate) {
                            return Free_digital_certificate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-digital-certificate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('free-digital-certificate.delete', {
            parent: 'free-digital-certificate',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-digital-certificate/free-digital-certificate-delete-dialog.html',
                    controller: 'Free_digital_certificateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Free_digital_certificate', function(Free_digital_certificate) {
                            return Free_digital_certificate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-digital-certificate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
