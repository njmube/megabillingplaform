(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('digital-certificate', {
            parent: 'entity',
            url: '/digital-certificate?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.digital_certificate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/digital-certificate/digital-certificates.html',
                    controller: 'Digital_certificateController',
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
                    $translatePartialLoader.addPart('digital_certificate');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('digital-certificate-detail', {
            parent: 'entity',
            url: '/digital-certificate/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.digital_certificate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/digital-certificate/digital-certificate-detail.html',
                    controller: 'Digital_certificateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('digital_certificate');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Digital_certificate', function($stateParams, Digital_certificate) {
                    return Digital_certificate.get({id : $stateParams.id});
                }]
            }
        })
        .state('digital-certificate.new', {
            parent: 'digital-certificate',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/digital-certificate/digital-certificate-dialog.html',
                    controller: 'Digital_certificateDialogController',
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
                                possword: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('digital-certificate', null, { reload: true });
                }, function() {
                    $state.go('digital-certificate');
                });
            }]
        })
        .state('digital-certificate.edit', {
            parent: 'digital-certificate',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/digital-certificate/digital-certificate-dialog.html',
                    controller: 'Digital_certificateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Digital_certificate', function(Digital_certificate) {
                            return Digital_certificate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('digital-certificate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('digital-certificate.delete', {
            parent: 'digital-certificate',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/digital-certificate/digital-certificate-delete-dialog.html',
                    controller: 'Digital_certificateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Digital_certificate', function(Digital_certificate) {
                            return Digital_certificate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('digital-certificate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
